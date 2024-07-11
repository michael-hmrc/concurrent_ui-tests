# shell.nix

{ pkgs ? import <nixpkgs> {} }:

pkgs.mkShell {
  buildInputs = with pkgs; [
    pkgs.openjdk11      # Java 11
    pkgs.geckodriver    # GeckoDriver for Firefox (replace with appropriate driver for your browser)
    pkgs.chromedriver   # ChromeDriver (replace with appropriate driver for your browser)
  ];

  # Set JAVA_HOME and PATH
  shellHook = ''
    export JAVA_HOME=${pkgs.openjdk11}/jre
    export PATH=${pkgs.openjdk11}/bin:$PATH
  '';
}
