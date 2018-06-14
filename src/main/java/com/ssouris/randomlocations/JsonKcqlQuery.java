package com.ssouris.randomlocations;

import com.datamountaineer.kcql.Kcql;
import com.fasterxml.jackson.databind.JsonNode;



public final class JsonKcqlQuery {

    public static JsonNode run(JsonNode value, Kcql ksql) {
        return new com.datamountaineer.json.kcql.JsonKcql.JsonKcqlConverter(value).kcql(ksql);
    }
}
