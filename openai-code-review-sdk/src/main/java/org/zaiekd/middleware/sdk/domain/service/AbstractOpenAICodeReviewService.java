package org.zaiekd.middleware.sdk.domain.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zaiekd.middleware.sdk.infrastructure.git.GitCommand;
import org.zaiekd.middleware.sdk.infrastructure.openai.IOpenAI;
import org.zaiekd.middleware.sdk.infrastructure.weixin.Weixin;

import java.io.IOException;

/**
 * @author lhz
 * @version 1.0
 * @create 2/16/25 2:28 PM
 */
public abstract class AbstractOpenAICodeReviewService implements IOpenAICodeReviewService {

    private static final Logger logger = LoggerFactory.getLogger(AbstractOpenAICodeReviewService.class);

    protected final GitCommand gitCommand;
    protected final IOpenAI openAI;
    protected final Weixin weixin;


    public AbstractOpenAICodeReviewService(GitCommand gitCommand, IOpenAI openAI, Weixin weixin) {
        this.gitCommand = gitCommand;
        this.openAI = openAI;
        this.weixin = weixin;
    }

    @Override
    public void exec() {
        try {
            // 1. 获取提交代码
            String diffCode = getDiffCode();
            // 2. 开始评审代码
            String recommend = codeReview(diffCode);
            // 3. 记录评审结果；返回日志地址
            String logUrl = recordCodeReview(recommend);
            // 4. 发送消息通知；日志地址、通知的内容
            pushMessage(logUrl);
        } catch (Exception e) {
            logger.error("openai-code-review error", e);
        }
    }

    protected abstract String getDiffCode() throws IOException, InterruptedException;

    protected abstract String codeReview(String diffCode) throws Exception;

    protected abstract String recordCodeReview(String recommend) throws Exception;

    protected abstract void pushMessage(String logUrl) throws Exception;
}
