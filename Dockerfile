FROM openjdk:21-slim

WORKDIR /app
ARG JAR_FILE
COPY target/${JAR_FILE} /app/customer-service.jar
COPY entrypoint.sh /app/entrypoint.sh

RUN adduser --system servuser
RUN chmod +x /app/entrypoint.sh
RUN chown -R servuser /app

USER servuser
EXPOSE 8080 8443
CMD ["/app/entrypoint.sh"]
