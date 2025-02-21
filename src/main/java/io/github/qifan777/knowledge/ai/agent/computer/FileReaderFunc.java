package io.github.qifan777.knowledge.ai.agent.computer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import io.github.qifan777.knowledge.ai.message.AiMessageController;
import io.github.qifan777.knowledge.infrastructure.config.EventUtil;
import io.github.qifan777.knowledge.infrastructure.config.MyFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.Description;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

@Component
@Description("读取用户指定文件的内容")
public class FileReaderFunc implements Function<FileReaderFunc.Request, String> {
    @Override
    public String apply(Request request) {
        // TODO 应该让系统有能力读取客户端文件
        try {
            HttpServletResponse response = AiMessageController.RESPONSE_MAP.get(request.uuid);
            EventUtil.sendEvent(true, response, ServerSentEvent.builder()
                    .data("{\"测试\": \"成功\"}").event("toolFunc").build());
        } catch (IOException ignored) {}

        File f = new File(request.path);
        String content = "";
        if (f.exists()) {
            try {
                content = FileUtils.readFileToString(f, "utf-8");
            } catch (Exception ignored) {}
        }
        return content;
    }

    public record Request(
            @JsonProperty(required = true) @JsonPropertyDescription(value = "uuid") String uuid,
            @JsonProperty(required = true) @JsonPropertyDescription("本机文件的绝对路径") String path
    ) {
    }
}
