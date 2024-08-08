# concurrent_ui-tests

### This repo is for UI/Acceptance tests for my React Blog  

### And

### Testing and writing example Selenium tests using Weaver tests and Scalatest

## To run all the tests and configure headless/non-headless and environment

```
sbt -Denvironment=local -Dheadless=true test
```

## To run a single test spec

```
sbt -Denvironment=local -Dheadless=true "testOnly *NavBarSpec*"
```

or run the shell scripts

### All tests for the React Blog
```bash
./run_blog_tests.sh
```

### Scalatest
```bash
./run_normal_tests.sh
```

### Weaver tests
```bash
./run_weaver_tests.sh
```

## Nix
If you have Nix installed you can use the nix shell to specify the jdk and chromedriver.
It will look at the contents of shell.nix for the packages it needs.

At the time of writing my Job is moving to JDK 21, some issues arose from specific Scala dependencies 
Rather than setting my local dev machine to JDK 8 or 11 to fix my sbt issue. 
I already had Nix installed so can utilise Nix for a temporary nix shell to set and point to JDK 11 

```bash
nix-shell
```

## Github actions 
Github actions runs the tests on every commit pushed to Github.


## Running the app

### Start the proxy server and proxy/test app
```
npm run proxy
```

### Proxy server host and port 

```
http://localhost:6060
```


### Start the app
```
npm start
```

### Localhost server host and port for actual app

```
http://localhost:3000
```




