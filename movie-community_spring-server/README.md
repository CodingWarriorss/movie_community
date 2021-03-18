# Movie community Server

- static resource 경로 설정 WebConfig 설정 관련 이슈


기본적인 Logger는 logback이 이미 내장되어있어서 dependency에 추가 할건 없는거 같습니다.


``` java
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyJavaCord{

    Logger logger = LoggerFactory.getLogger(this.getClass());

    public myMethod(){
        logger.info("Info 입니다.");
        logger.error("error 입니다.");
        logger.debug("debug 입니다.");
    }
    ...
}
```

예시 코드 처럼 클래스 변수로 선언한뒤에 로그 레벨에 맏게 함수를 호출해서 쓰면 됩니다.
깊이있게 쓰는 방법을 또 찾아봅시다...