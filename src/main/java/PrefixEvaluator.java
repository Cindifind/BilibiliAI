import java.util.Stack;

public class PrefixEvaluator {

    // 计算前缀表达式
    public static int evaluatePrefix(String expr) {
        Stack<Integer> stack = new Stack<>();

        // 从右到左遍历表达式
        for (int i = expr.length() - 1; i >= 0; i--) {
            char ch = expr.charAt(i);

            if (Character.isDigit(ch)) {
                stack.push(ch - '0');
            } else {
                int operand1 = stack.pop();
                int operand2 = stack.pop();

                switch (ch) {
                    case '+':
                        stack.push(operand1 + operand2);
                        break;
                    case '-':
                        stack.push(operand1 - operand2);
                        break;
                    case '*':
                        stack.push(operand1 * operand2);
                        break;
                    case '/':
                        stack.push(operand1 / operand2);
                        break;
                }
            }
        }
        return stack.pop();
    }

    public static void main(String[] args) {
        String prefixExpr = "-*1+234 "; // 对应的中缀表达式是 5 + (3 * 2)

        // 去除空格以便解析
        String cleanedExpr = prefixExpr.replaceAll("\\s+", "");

        int result = evaluatePrefix(cleanedExpr);
        System.out.println("Prefix expression result: " + result); // 输出 11
    }
}
