import org.junit.Test
import org.junit.Assert.*


class MainKtTest {

    @Test
    fun limit_VKPay_Payment() {
        // arrange
        val amount = 15_000
        val type = ACCOUNT_TYPE_VK_PAY

        // act
        val limitMessage = limit(
            transferAmount = amount,
            userType = type
        )
        // assert
        assertEquals(MESSAGE_PAYMENT, limitMessage)
    }

    @Test
    fun limit_VKPay_Month() {
        val amount = 45_000
        val type = ACCOUNT_TYPE_VK_PAY

        val limitMessage = limit(
            transferAmount = amount,
            userType = type
        )

        assertEquals(MESSAGE_MONTH, limitMessage)
    }

    @Test
    fun limit_VKPay_else() {
        val amount = 8_000
        val type = ACCOUNT_TYPE_VK_PAY

        val limitMessage = limit(
            transferAmount = amount,
            userType = type
        )

        assertEquals(MESSAGE_COMMISSION, limitMessage)

    }

    @Test
    fun limit_Mir_else() {
        val amount = 10_000
        val type = ACCOUNT_TYPE_MIR

        val limitMessage = limit(
            transferAmount = amount,
            userType = type
        )

        assertEquals(MESSAGE_COMMISSION, limitMessage)
    }

    @Test
    fun limit_Master() {
        val amount = 150_000
        val type = ACCOUNT_TYPE_MASTER

        val limitMessage = limit(
            transferAmount = amount,
            userType = type
        )

        assertEquals(MESSAGE_DAY, limitMessage)
    }

    @Test
    fun limit_Visa() {

        val amount = 655_000
        val type = ACCOUNT_TYPE_VISA

        val limitMessage = limit(
            transferAmount = amount,
            userType = type
        )

        assertEquals(MESSAGE_MONTH, limitMessage)
    }

    @Test
    fun commissionCalculate_Visa() {
        val amount = 80_000
        val last = 0
        val type = ACCOUNT_TYPE_VISA

        val commissionSum = commissionCalculate(
            transferAmount = amount,
            previousTransfer = last,
            userType = type
        )

        assertEquals(60_000, commissionSum)
    }

    @Test
    fun commissionCalculate_Maestro_else() {
        val amount = 50_000
        val last = 5_000
        val type = ACCOUNT_TYPE_MAESTRO

        val commissionSum = commissionCalculate(
            transferAmount = amount,
            previousTransfer = last,
            userType = type
        )

        assertEquals(0, commissionSum)
    }

    @Test
    fun commissionCalculate_VKPay() {
        val amount = 10_000
        val last = 0
        val type = ACCOUNT_TYPE_VK_PAY

        val commissionSum = commissionCalculate(
            transferAmount = amount,
            previousTransfer = last,
            userType = type
        )

        assertEquals(0, commissionSum)
    }

    @Test
    fun commissionCalculate_Master() {
        val amount = 75_000
        val last = 0
        val type = ACCOUNT_TYPE_MASTER

        val commissionSum = commissionCalculate(
            transferAmount = amount,
            previousTransfer = last,
            userType = type
        )

        assertEquals(47_000, commissionSum)
    }

    @Test
    fun commissionCalculate_Visa_minCommission() {
        val amount = 4_000
        val last = 0
        val type = ACCOUNT_TYPE_VISA

        val commissionSum = commissionCalculate(
            transferAmount = amount,
            previousTransfer = last,
            userType = type
        )

        assertEquals(3_500, commissionSum)
    }

    @Test
    fun commissionCalculate_Mir_minCommission() {
        val amount = 4_000
        val last = 0
        val type = ACCOUNT_TYPE_MIR

        val commissionSum = commissionCalculate(
            transferAmount = amount,
            previousTransfer = last,
            userType = type
        )

        assertEquals(3_500, commissionSum)
    }

    @Test
    fun commissionCalculate_Mir() {
        val amount = 70_000
        val last = 0
        val type = ACCOUNT_TYPE_MIR

        val commissionSum = commissionCalculate(
            transferAmount = amount,
            previousTransfer = last,
            userType = type
        )

        assertEquals(52_500, commissionSum)
    }

    @Test
    fun commissionCalculate_Maestro() {
        val amount = 75_000
        val last = 0
        val type = ACCOUNT_TYPE_MAESTRO

        val commissionSum = commissionCalculate(
            transferAmount = amount,
            previousTransfer = last,
            userType = type
        )

        assertEquals(47_000, commissionSum)
    }

    @Test
    fun limit_Master_else() {
        val amount = 10_000
        val type = ACCOUNT_TYPE_MASTER

        val limitMessage = limit(
            transferAmount = amount,
            userType = type
        )

        assertEquals(MESSAGE_COMMISSION, limitMessage)
    }

    @Test(expected = IllegalStateException::class)
    fun limit_error() {
        val amount = 0
        val type = MESSAGE_COMMISSION

        val limitMessage = limit(
            transferAmount = amount,
            userType = type
        )

        assertEquals("Неподдерживаемый тип платежной системы", limitMessage)
    }

    @Test(expected = IllegalStateException::class)
    fun commissionCalculate_error() {
        val amount = 80_000
        val last = 0
        val type = MESSAGE_COMMISSION

        val commissionSum = commissionCalculate(
            transferAmount = amount,
            previousTransfer = last,
            userType = type
        )

        assertEquals("Неподдерживаемый тип платежной системы", commissionSum)
    }
}