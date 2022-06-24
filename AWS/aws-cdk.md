# AWS Cloud Development Kit
1. install `>_ npm install -g aws-cdk`
1. `>_ cdk synth` requires you to be in the same directory as your cdk.json. AWS CDK apps are effectively only a definition of your infrastructure using code. When CDK apps are executed, they produce (or “synthesize”, in CDK parlance) an AWS CloudFormation template for each stack defined in your application.
```
>_ mkdir cdk-demo
>_ cd cdk-demo
>_ cdk init --language typescript
```
1. `>_ cdk bootstrap`  The first time you deploy an AWS CDK app into an environment (account/region), you can install a “bootstrap stack”. This stack includes resources that are used in the toolkit’s operation.  
"CDK stacks" - i.e. combinations of Lambdas and Docker images etc - are first uploaded to an S3 bucket (or other container) so they are available to "CloudFormation" during deployment.  These containers need to exist ahead of deploy - so that they are accessible, IN THE AWS ACCOUNT AND REGION YOU ARE DEPLOYING TO.  Hence first time you deploy an AWS CDK app into an environment (account/region), you can install a “bootstrap stack” (i.e. 'bootstrapping'). This stack includes resources that are used in the toolkit’s operation.  Bootstrap your AWS account and given region, via,
```
# Get the account ID with 'aws sts get-caller-identity'
>_ aws sts get-caller-identity --profile josephh_admin
{
    "UserId": "AIDAZSEDIOB3CAYKDEMON",
    "Account": "657405276278",
    "Arn": "arn:aws:iam::657405276278:user/josephh_admin"
}
# Bootstrap the account (--profile ... only required in the absence of default credentials) with 'cdk bootstrap aws://ACCOUNT-NUMBER/REGION --profile ...'
>_ cdk bootstrap aws://657405276278/eu-west-1 --profile josephh_admin
⏳  Bootstrapping environment aws://657405276278/eu-west-1...
Trusted accounts for deployment: (none)
Trusted accounts for lookup: (none)
Using default execution policy of 'arn:aws:iam::aws:policy/AdministratorAccess'. Pass '--cloudformation-execution-policies' to customize.
CDKToolkit: creating CloudFormation changeset...
[█████████▋················································] (2/12)
11:52:20 | CREATE_IN_PROGRESS   | AWS::CloudFormation::Stack | CDKToolkit
11:52:26 | CREATE_IN_PROGRESS   | AWS::IAM::Role        | ImagePublishingRole
11:52:26 | CREATE_IN_PROGRESS   | AWS::IAM::Role        | FilePublishingRole
11:52:26 | CREATE_IN_PROGRESS   | AWS::IAM::Role        | CloudFormationExecutionRole
11:52:26 | CREATE_IN_PROGRESS   | AWS::IAM::Role        | LookupRole
11:52:26 | CREATE_IN_PROGRESS   | AWS::S3::Bucket       | StagingBucket  
```  
1. `>_ cdk deploy`
1. Each CDK stack maps 1:1 with [CloudFormation]() stack.
* To start building out a project, a common starting point is to create a logically isolated virtual network that you define, called an Amazon Virtual Private Cloud (VPC). Before we create our first VPC, we need to understand the files that the cdk init command created.
* Demo VPC has two public-facing subnets, spread across two Availability Zones.  Library modules need installing first - these are packaged in such a way as to only need to add the services dependencies you require for the infrastructure you are provisioning.  (Modules can either be for a single service, e.g. AWS Amplify, or for multiple services, e.g. Amazon EC2).  `npm install @aws-cdk/aws-ec2`.  Then begin to amend code as per the demo tutorial... see [Code for the VPC](https://aws.amazon.com/getting-started/guides/setup-cdk/module-three/).
* When creating a VPC, there are a number of properties we can set to tailor it to our needs. By default, it will create a VPC across 3 availability zones (AZs), with public and private subnets (with a single Internet Gateway and 3 NAT Gateways). `npm run build` for the demo project compiles the typescript; then  ...?  
1. When changing the details of a cdk stack - in your project code/ config - the command `cdk diff` can be run (with `cdk diff --profile josephh_admin` where necessary) to show a dry-run of what's been added or destroyed.
