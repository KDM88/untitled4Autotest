const val ACCOUNT_TYPE_MASTER = "MasterCard, Maestro"
const val ACCOUNT_TYPE_MAESTRO = "Maestro"
const val ACCOUNT_TYPE_VISA = "Visa"
const val ACCOUNT_TYPE_VK_PAY = "VK Pay"
const val ACCOUNT_TYPE_MIR = "МИР"
const val MESSAGE_MONTH = "Превышена сумма месячного перевода средств"
const val MESSAGE_PAYMENT = "Превышена сумма перевода за одну транзакцию"
const val MESSAGE_DAY = "Превышена сумма перевода в сутки"
const val MESSAGE_COMMISSION = "Комиссия составляет: "

fun main() {
    val limitMessage = limit(10_000)
    val sum = commissionCalculate(10_000)
    val result = if (limitMessage == MESSAGE_COMMISSION) ("$MESSAGE_COMMISSION $sum копеек") else (limitMessage)
    println(result)
}

fun limit(transferAmount: Int, previousTransfer: Int = 0, userType: String = ACCOUNT_TYPE_VK_PAY): String {
    return  when (userType) {
        ACCOUNT_TYPE_VK_PAY -> {
            if ((transferAmount + previousTransfer) >= 40_000) MESSAGE_MONTH
            else if (transferAmount >= 15_000) MESSAGE_PAYMENT
            else MESSAGE_COMMISSION
        }
        ACCOUNT_TYPE_MASTER, ACCOUNT_TYPE_MAESTRO, ACCOUNT_TYPE_VISA, ACCOUNT_TYPE_MIR -> {
            if ((transferAmount + previousTransfer) >= 600_000) MESSAGE_MONTH
            else if  ((transferAmount + previousTransfer) >= 150_000) MESSAGE_DAY
            else MESSAGE_COMMISSION
        }
        else -> error("Неподдерживаемый тип платежной системы")
    }
}

fun commissionCalculate(transferAmount: Int, previousTransfer: Int = 0, userType: String = ACCOUNT_TYPE_VK_PAY): Int {

    val commissionSum = when (userType) {
        ACCOUNT_TYPE_VK_PAY -> 0
        ACCOUNT_TYPE_MASTER, ACCOUNT_TYPE_MAESTRO -> when (transferAmount) {
            in 75_000..150_000 -> (((transferAmount + previousTransfer) * 0.006 * 100) + 2_000).toInt()
            else -> 0
        }
        ACCOUNT_TYPE_VISA, ACCOUNT_TYPE_MIR -> {
            val minCommission = 3_500
            val calculate = (transferAmount * 0.0075 * 100).toInt()
            if (calculate < minCommission) minCommission else calculate
        }
        else -> error("Неподдерживаемый тип платежной системы")
    }
    return commissionSum
}