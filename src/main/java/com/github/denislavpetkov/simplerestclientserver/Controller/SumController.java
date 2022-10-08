package com.github.denislavpetkov.simplerestclientserver.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.denislavpetkov.simplerestclientserver.DTO.Sum;
import org.springframework.web.bind.annotation.*;
import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/sum")
public class SumController {

    String dataFilePath = System.getProperty("user.dir")+"/data/sum.xml";

    @GetMapping
    public Sum sum() throws IOException {
        ObjectMapper xmlMapper = new XmlMapper();
        Sum sum = xmlMapper.readValue(new File(dataFilePath),Sum.class);
        return sum;
    }

    @PostMapping("/create")
    public Sum newSum(@RequestBody Sum newSum) throws IOException{
        newSum.setResult(newSum.getNum1() + newSum.getNum2());
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.writeValue(new File(dataFilePath), newSum);
        return newSum;
    }

    @GetMapping("/update")
    public Sum updateSum(@RequestParam double num1, @RequestParam double num2) throws IOException {
        Sum newSum = new Sum(num1, num2);
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.writeValue(new File(dataFilePath), newSum);
        return newSum;
    }

}

