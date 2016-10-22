package home.loggings

import groovy.util.logging.Slf4j
import home.loggings.logs.Log
import home.loggings.logs.LogRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RequestMapping(path = "/log")
@RestController
@Slf4j
class LogResource {

    @Autowired
    private LogRepository logRepository;

    @RequestMapping(path = "" ,method = RequestMethod.POST)
    void createLog(@RequestParam(required = false) String type, @RequestBody String body){
        log.info(" webhook-payload :${type} : " + body)
        Log logEntity = new Log()
        logEntity.content = body
        logEntity.created = new Date()
        logEntity.type = type
        logRepository.save(logEntity)
    }
}
