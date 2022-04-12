FROM alpine:3.15.4

ARG AWS_CDK_VERSION=2.18.0

# https://ittutorialpoint.com/how-to-install-python-in-alpine-linux/
RUN apk add python3 py3-pip
RUN python3 --version
RUN pip3 --version

# https://cloudaffaire.com/faq/how-to-install-aws-cli-on-alpine/
# https://docs.aws.amazon.com/systems-manager/latest/userguide/session-manager-working-with-install-plugin.html
RUN pip3 install awscli
RUN aws --version

# https://velog.io/@ywoosang/Node.js-%EC%84%A4%EC%B9%98
RUN apk add nodejs npm
RUN node -v

RUN npm install -g aws-cdk@${AWS_CDK_VERSION}

VOLUME [ "/opt/app" ]
VOLUME [ "/root/.aws" ]

RUN apk add openjdk11
RUN java -version
ENV GRADLE_USER_HOME=/opt/app/build/gradle

WORKDIR /opt/app
