package com.ssouris.randomlocations.ql

import com.datamountaineer.kcql.Kcql

/**
 * Creates a kafka streams application based on a SQL query
 * then returns a in instance Subject that can be subscribed and used to publish to websockets.
 *
 */
data class KafkaTopicQueryLanguage(val source: String, val target: String, val sql: String, val ksql: Kcql) {
    companion object {
        fun parse(sql: String): KafkaTopicQueryLanguage {
            val kcql = Kcql.parse(sql)
            return KafkaTopicQueryLanguage(kcql.source, kcql.target, sql, kcql)
        }
    }
}
