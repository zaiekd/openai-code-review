curl -X POST \
        -H "Authorization: Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiIsInNpZ25fdHlwZSI6IlNJR04ifQ.eyJhcGlfa2V5IjoiOTM2ODE2ZmEwMjAwNGFiZDlhM2E3YWFhZTUxZDcwYWEiLCJleHAiOjE3Mzk2NTM2MzU1NjEsInRpbWVzdGFtcCI6MTczOTY1MTgzNTU2N30.copA9tiFaH8M57LOQYUQPV3SxukoRF4M3iicBeE9m-8" \
        -H "Content-Type: application/json" \
        -H "User-Agent: Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)" \
        -d '{
          "model":"glm-4",
          "stream": "true",
          "messages": [
              {
                  "role": "user",
                  "content": "1+1"
              }
          ]
        }' \
  https://open.bigmodel.cn/api/paas/v4/chat/completions
