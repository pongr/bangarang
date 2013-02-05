package com.pongr.bangarang

/*
aws-java-sdk provides AmazonRoute53
Bangarang could provide some nice wrappers around that
Could then have an executable fat jar to easily change a dns record from bash, something like:
  java -jar fat.jar sub.domain.com 11.22.33.44
Could then wrap that in other bash scripts, add to $PATH, something like:
  route53 create sub.domain.com 11.22.33.44
And then put all of that in a Debian pkg in our apt repo, install on boot:
  sudo apt-get install -y --force-yes pongr-route53
*/

package object route53 {

}
