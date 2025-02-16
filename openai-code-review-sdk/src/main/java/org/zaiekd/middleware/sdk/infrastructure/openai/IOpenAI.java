package org.zaiekd.middleware.sdk.infrastructure.openai;

import org.zaiekd.middleware.sdk.infrastructure.openai.dto.ChatCompletionRequestDTO;
import org.zaiekd.middleware.sdk.infrastructure.openai.dto.ChatCompletionSyncResponseDTO;

public interface IOpenAI {
    ChatCompletionSyncResponseDTO completions(ChatCompletionRequestDTO requestDTO) throws Exception;
}
