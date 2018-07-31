package com.frwan.demo.controllers;

import com.frwan.demo.proto.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/test")
public class TestController {

    @ApiOperation(value = "message")
    @RequestMapping(value = "message", method = RequestMethod.GET)
    public String GetTestMessage() {
        Message.Person.Builder personBuilder = Message.Person.newBuilder();
        personBuilder.setId(12345678);
        personBuilder.setName("Admin");
        personBuilder.addPhone(Message.Person.Phone.newBuilder().setNumber("10010").setType(Message.Person.PhoneType.MOBILE));
        personBuilder.addPhone(Message.Person.Phone.newBuilder().setNumber("10086").setType(Message.Person.PhoneType.HOME));
        personBuilder.addPhone(Message.Person.Phone.newBuilder().setNumber("10000").setType(Message.Person.PhoneType.WORK));

        Message.Person person = personBuilder.build();
        byte[] buff = person.toByteArray();
        try {
            Message.Person personOut = Message.Person.parseFrom(buff);
            System.out.printf("Id:%d, Name:%s\n", personOut.getId(), personOut.getName());

            List<Message.Person.Phone> phoneList = personOut.getPhoneList();

            for (Message.Person.Phone phone : phoneList) {
                System.out.printf("PhoneNumber:%s (%s)\n", phone.getNumber(), phone.getType());
            }

        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }

        System.out.println(Arrays.toString(buff));
        return Arrays.toString(buff);
    }
}
