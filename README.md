# concurrent_ui-tests

Testing and writing example Selenium tests using Weaver tests and Scalatest


## To run the tests

```
sbt test
```

or run the shell scripts

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
