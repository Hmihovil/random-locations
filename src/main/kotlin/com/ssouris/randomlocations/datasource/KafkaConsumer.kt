package com.ssouris.randomlocations.datasource

import com.fasterxml.jackson.databind.JsonNode
import com.ssouris.randomlocations.JsonKcqlQuery.run
import com.ssouris.randomlocations.ql.KafkaTopicQueryLanguage
import io.reactivex.subjects.PublishSubject
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.common.serialization.Serdes.serdeFrom
import org.apache.kafka.connect.json.JsonDeserializer
import org.apache.kafka.connect.json.JsonSerializer
import org.apache.kafka.streams.Consumed.with
import org.apache.kafka.streams.KafkaStreams
import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.StreamsConfig
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.DisposableBean
import org.springframework.boot.autoconfigure.kafka.KafkaProperties
import java.util.*


class KafkaConsumer : DisposableBean {

    companion object {
        val log: Logger = LoggerFactory.getLogger(KafkaConsumer::class.java)
    }

    private var stream: KafkaStreams? = null
    private var rxSubject: PublishSubject<Pair<KafkaTopicQueryLanguage, JsonNode>>? = null

    fun observe(sql: String, kafkaProperties: KafkaProperties) =
            startKafkaStreams(KafkaTopicQueryLanguage.parse(sql), kafkaProperties)

    override fun destroy() {
        this.stream?.close()
        this.rxSubject?.onComplete()
    }

    private fun startKafkaStreams(kafkaTopicQueryLanguage: KafkaTopicQueryLanguage, kafkaProperties: KafkaProperties): PublishSubject<Pair<KafkaTopicQueryLanguage, JsonNode>> {

        val props = Properties()
        props.loadFrom(kafkaProperties.buildConsumerProperties())
        props[StreamsConfig.APPLICATION_ID_CONFIG] = UUID.randomUUID().toString()

        val subject = PublishSubject.create<Pair<KafkaTopicQueryLanguage, JsonNode>>()
        val topologyBuilder = StreamsBuilder()
        topologyBuilder.stream(kafkaTopicQueryLanguage.source, with(Serdes.String(), serdeFrom(JsonSerializer(), JsonDeserializer())))
                .map { key, value -> KeyValue(key, runQuery(value, kafkaTopicQueryLanguage)) }
                .foreach { _, value -> subject.onNext(kafkaTopicQueryLanguage to value) }

        val streams = KafkaStreams(topologyBuilder.build(), props)
        log.info("Starting $props ${kafkaTopicQueryLanguage.source} -> ${kafkaTopicQueryLanguage.target} ")
        streams.start()
        stream = streams
        rxSubject = subject
        return subject
    }

    private fun runQuery(value: JsonNode, mainSql: KafkaTopicQueryLanguage): JsonNode = run(value, mainSql.ksql)

    private fun Properties.loadFrom(properties: Map<String, Any>) = properties.forEach { this[it.key] = it.value }

}



