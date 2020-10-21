package wccli

object Main extends App {
  FileUtil.attemptDeleteFile("mytestfile.txt")
  //new Cli().menu()
}
