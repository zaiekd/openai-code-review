package org.zaiekd.middleware.sdk;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zaiekd.middleware.sdk.domain.service.impl.OpenAICodeReviewService;
import org.zaiekd.middleware.sdk.infrastructure.git.GitCommand;
import org.zaiekd.middleware.sdk.infrastructure.openai.IOpenAI;
import org.zaiekd.middleware.sdk.infrastructure.openai.impl.ChatGLM;
import org.zaiekd.middleware.sdk.infrastructure.weixin.Weixin;

/**
 * @author lhz
 * @version 1.0
 * @create 2/8/25 4:23 PM
 */
public class OpenAiCodeReview {

    private static final Logger logger = LoggerFactory.getLogger(OpenAiCodeReview.class);

    // 实际上是从github action里获取，而不是这里，见下面代码
    // 配置配置
    private String weixin_appid = "wx4060cd6b7e88fdab";
    private String weixin_secret = "264da0ad2d8620744c27ca81cade8272";
    private String weixin_touser = "oXVlR7D_TDTMLRXPPslyvIcC2NFE";
    private String weixin_template_id = "rtU46v3GMGESJzrEgLgflA1X790LWiGazERJZaAvkIA";

    // ChatGLM 配置
    private String chatglm_apiHost = "https://api.deepseek.com/chat/completions";
    private String chatglm_apiKeySecret = "";

    // Github 配置
    private String github_review_log_uri;
    private String github_token;

    // 工程配置 - 自动获取
    private String github_project;
    private String github_branch;
    private String github_author;

    public static void main(String[] args) throws Exception {
        System.out.println("Openai Test");

        GitCommand gitCommand = new GitCommand(
                getEnv("GITHUB_REVIEW_LOG_URI"),
                getEnv("GITHUB_TOKEN"),
                getEnv("COMMIT_PROJECT"),
                getEnv("COMMIT_BRANCH"),
                getEnv("COMMIT_AUTHOR"),
                getEnv("COMMIT_MESSAGE")
        );

        /**
         * 项目：{{repo_name.DATA}} 分支：{{branch_name.DATA}} 作者：{{commit_author.DATA}} 说明：{{commit_message.DATA}}
         */
        Weixin weiXin = new Weixin(
                getEnv("WEIXIN_APPID"),
                getEnv("WEIXIN_SECRET"),
                getEnv("WEIXIN_TOUSER"),
                getEnv("WEIXIN_TEMPLATE_ID")
        );


        IOpenAI openAI = new ChatGLM(getEnv("CHATGLM_APIHOST"), getEnv("CHATGLM_APIKEYSECRET"));

        OpenAICodeReviewService openAiCodeReviewService = new OpenAICodeReviewService(gitCommand, openAI, weiXin);
        openAiCodeReviewService.exec();

        logger.info("openai-code-review done!");
    }

    private static String getEnv(String key) {
        String value = System.getenv(key);
        if (null == value || value.isEmpty()) {
            throw new RuntimeException("value is null");
        }
        return value;
    }

}
