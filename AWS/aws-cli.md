1. install aws cli. then run e.g.  `aws cognito-idp help`
  1. confirm installation
  ```
jjobbings@josephs-MacBook-Pro Downloads % which aws
/usr/local/bin/aws
jjobbings@josephs-MacBook-Pro Downloads % aws --version
aws-cli/2.7.4 Python/3.9.11 Darwin/21.1.0 exe/x86_64 prompt/off  
```
1. use the aws IAM web portal to create an IAM user, generate and export access keys (as .csv file)
1. configure aws run e.g. `aws configure import --csv file://./josephh_admin_aws_credentials.csv` (Note MUST include `file://` prefix)
1. specify different profiles as needed, e.g. `aws configure --profile produser`.  (:warning: a profile is prefixed with `[profile ...]` only in the config file, not the credentials file).
1. use the profile with different services via the '--profile' switch, e.g. `aws s3 ls --profile produser`
1. inside user root home directory hidden aws folder, default and profile credentials go in 'credentials' file while default and profile output format and region values go in 'config'.
1. read and write individual settings values with `aws configure get` and `aws configure set`; view all config with `aws configure list`
1. `aws configure list-profiles`
1. single sign-on exists - if AWS IAM is connected, e.g. to a corporate active directory.  Users can sign-in, e.g. using their AD account, which will be mapped to an AWS IAM account and then used to run AWS commands and use AWS services.
1. Environment variables, starting `AWS_` (e.g. AWS_ACCESS_KEY_ID) can be set, instead of credentials/ profile files.
1. Command line options can also be used.  These will override default configuration settings, any corresponding profile setting, or environment variable setting for that single command.
1. add the shell completion tool ("aws completer").  Find the installed location - `which aws_completer`; find out which shell you are using `echo $SHELL`.   See the docs for commands that need adding to .zshrc.
