fun main(args: Array<String>) {
    val replaceToken = "\$p$"
    val username = "Veritaris"
    val command = "kick \$p$ because \$p$ is too good developer"
    println(command.replace(replaceToken, username))
}