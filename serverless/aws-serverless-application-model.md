# AWS Serverless Application Model (SAM)
install on Mac SAM via brew...
```
brew --version
brew tap aws/tap\nbrew install aws-sam-cli
sam --version
```
create project - interactive, guided setup... e.g. choose node and zip packaging (AWS Quick Start application template 1 - Hello World Example ...)  
```
sam init
```
run locally
```
sam build
sam local invoke
(or ...) sam local invoke HelloWorldFunction --event events/event.json
sam local start-api
```
attach to logs in cloudformation "stack" (remote rather than local - swap in values as needed, or simply run `sam logs` to see logs from all deployed stacks)
```
sam logs -n HelloWorldFunction --stack-name hello-world-stack --tail
```
## aws CLI
### Commands
Many of these commands are helpful to confirm creation of new resources following a cloudformation provision/ deployment
#### write local .aws config and credentials files
`aws configure`
#### show user group

### show buckets
`aws s3 ls`
2022-03-19 21:55:20 amplify-amplified-dev-215506-deployment
2022-10-13 12:43:26 aws-sam-cli-managed-default-samclisourcebucket-1asgtt64981ow
...
#### list stacks
`aws cloudformation list-stacks`
{
    "StackSummaries": [
        {
            "StackId": "arn:aws:cloudformation:eu-west-2:657405276278:stack/sam-helloworld-app/41baeca0-4aec-11ed-a1d9-0a63690ef93e",
            "StackName": "sam-helloworld-app",
            "TemplateDescription": "hello-world\nSample SAM Template for hello-world\n",
            "CreationTime": "2022-10-13T11:43:29.493000+00:00",
            "LastUpdatedTime": "2022-10-13T11:43:34.936000+00:00",
            "StackStatus": "CREATE_COMPLETE",
            "DriftInformation": {
                "StackDriftStatus": "NOT_CHECKED"
            }
        },
        {
            "StackId": "arn:aws:cloudformation:eu-west-2:657405276278:stack/aws-sam-cli-managed-default/251dca40-4aec-11ed-a8ed-02e638078d9a",
            "StackName": "aws-sam-cli-managed-default",
            "TemplateDescription": "Managed Stack for AWS SAM CLI",
            "CreationTime": "2022-10-13T11:42:41.430000+00:00",
            ...
  etc ...
