package io.github.qifan777.knowledge.ai.agent.computer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.github.qifan777.knowledge.ai.agent.AbstractAgent;
import io.github.qifan777.knowledge.ai.agent.Agent;
import io.github.qifan777.knowledge.infrastructure.config.MyFilter;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Description;
import org.springframework.http.codec.ServerSentEvent;

import java.io.IOException;
import java.util.function.Function;

@Agent
@Description("提供关于当前主机的cpu，文件，文件夹相关问题的有用回答")
@AllArgsConstructor
public class ComputerAssistant extends AbstractAgent implements Function<ComputerAssistant.Request, String> {
    private final ChatModel chatModel;

    @Override
    public String apply(Request request) {
        return ChatClient.create(chatModel)
                .prompt()
                .functions(getFunctions(CpuAnalyzer.class, DirectoryReader.class, FileReaderFunc.class))
                .system("uuid: " + request.uuid())
                .user(request.query())
                .call()
                .content();
    }

    public record Request(
            @JsonProperty(required = true) @JsonPropertyDescription(value = "uuid") String uuid,
            @JsonProperty(required = true) @JsonPropertyDescription(value = "用户原始的提问") String query) {
    }


}