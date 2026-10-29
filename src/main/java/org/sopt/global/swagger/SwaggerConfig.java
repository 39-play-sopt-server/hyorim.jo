package org.sopt.global.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.sopt.global.code.SuccessCode;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    // 모든 API에 Member-Id 헤더 인증 적용
    // 필요 없으면 @SecurityRequirements로 제외
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components().addSecuritySchemes("memberId", new SecurityScheme().type(SecurityScheme.Type.APIKEY).in(SecurityScheme.In.HEADER).name("Member-Id")))
                .addSecurityItem(new SecurityRequirement().addList("memberId"));
    }

    @Bean
    public OperationCustomizer customize() {
        return (operation, handlerMethod) -> {
            // 성공
            ApiSuccessCode successAnnotation = handlerMethod.getMethodAnnotation(ApiSuccessCode.class);
            if (successAnnotation != null) {
                applySuccessCode(operation, successAnnotation.value());
            }

            return operation;
        };
    }

    // @ApiSuccessCode 의 상태 코드로 변경
    private void applySuccessCode(Operation operation, SuccessCode successCode) {
        ApiResponses responses = operation.getResponses();
        var response = responses.remove("200");
        if (response == null) {
            return;
        }

        String statusCode = String.valueOf(successCode.getHttpStatus().value());
        responses.addApiResponse(statusCode, response.description(successCode.getMessage()));
    }
}
