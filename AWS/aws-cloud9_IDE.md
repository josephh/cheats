Note using `... --profile josephh_admin` in most of these commands - this is in absence of a `[default]` entry in the local aws credentials file.

1. `aws cloud9 create-environment-ec2 --name getting-started --description "Getting started with AWS Cloud9." --instance-type t3.micro --automatic-stop-time-minutes 60 --profile josephh_admin` (Note: 't3.micro' instance is covered by the AWS Free Tier for the first 12 months.  Note: The `--automatic-stop-time-minutes` will automatically shut down the instance after the period of minutes has elapsed which will help keep you within the 750h / month free tier).
1. this command responds with an instance - use it to go to the IDE console in a web browser - https://console.aws.amazon.com/cloud9/ide/<environment ID>?region=us-west-2
e.g. `https://console.aws.amazon.com/cloud9/ide/bd6fec6570f347dd8cbc0b58587c2a4a?region=eu-west-2`
