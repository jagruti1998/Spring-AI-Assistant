package com.ai.ai_backend.service;

import com.ai.ai_backend.payload.CricketResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@Service
public class ChatService {

    @Autowired
    private ChatModel chatModel;

    public String generateResponse(String inputText){
        //using ai
        String response = chatModel.call(inputText);
        return response;

    }
    public Flux<String> streamResponse(String inputText){
        //using ai
        Flux<String>response = chatModel.stream(inputText);
        return response;

    }

    public CricketResponse generateCricketResponse(String inputText) throws JsonProcessingException {
        String promptString= "";
        ChatResponse cricketResponse = chatModel.call(
                new Prompt(promptString)
        );

        //get content as string
        String responseString=cricketResponse.getResult().getOutput().toString();
        ObjectMapper mapper=new ObjectMapper();
        CricketResponse cricketResponse1=mapper.readValue(responseString, CricketResponse.class);
        return cricketResponse1;

    }
    //load prompt from classpath
    public String loadPromptTemplate(String fileName) throws IOException {
        Path filePath=new ClassPathResource(fileName).getFile().toPath();
        return Files.readString(filePath);
    }

    //putvalues to prompt
    public String putValuesInPromptTemplate(String template, Map<String,String> variables){

        for(Map.Entry<String,String>entry: variables.entrySet()){
            template=template.replace("{" +entry.getKey() + "}" , entry.getValue());
        }
        return template;

    }

}
