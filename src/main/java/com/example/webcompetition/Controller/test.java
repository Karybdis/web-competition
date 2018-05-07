package com.example.webcompetition.Controller;



import com.example.webcompetition.Entity.User0;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class test {

    @GetMapping("/user")
    @ResponseBody
    public Object test()
    {
        User0 user=new User0();
        user.setAge(10);
        user.setName("sun");
        return user;
    }


}
