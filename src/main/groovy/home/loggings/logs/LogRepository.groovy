package home.loggings.logs

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository
import org.springframework.data.repository.query.Param
import org.springframework.data.rest.core.annotation.RestResource
import org.springframework.web.bind.annotation.RequestMapping

@RequestMapping("/logs")
interface LogRepository extends ElasticsearchRepository<Log, String>{
    @RestResource(path = "type", rel = "type")
    Page<Log> findByTypeOrderByCreatedDesc(@Param("name") String type, Pageable p)
}
