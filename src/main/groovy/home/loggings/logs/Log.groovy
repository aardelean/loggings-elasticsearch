package home.loggings.logs

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import org.springframework.data.elasticsearch.annotations.Field
import org.springframework.data.elasticsearch.annotations.FieldType

@Document(indexName = "webhooks", type = "logs")
class Log {
    @Id
    String id
    String content
    Date created
    @Field(type = FieldType.String, store = true)
    String type
}
