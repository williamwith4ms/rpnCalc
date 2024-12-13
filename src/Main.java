import java.util.*;

class RPN {
    Stack<String> expression = new Stack<>();
    Stack<String> operators = new Stack<>();
    Dictionary<String, Integer> precedence = new Hashtable<>();

    public RPN() {
        precedence.put("+", 1);
        precedence.put("-", 1);
        precedence.put("*", 2);
        precedence.put("/", 2);
    }

    void add_operand(String operand) {
        expression.add(operand);
    }

    void add_operator(String operator) {
        while (!operators.isEmpty()
                && precedence.get(operators.peek()) != null
                && precedence.get(operators.peek()) >= precedence.get(operator)) {
            expression.add(operators.pop());
        }
        operators.add(operator);
    }

    void format_expression(String expression) {
        String[] entities = expression.split(" ");

        for (String entity : entities) {
            if (precedence.get(entity) == null) {
                add_operand(entity);
            } else {
                add_operator(entity);
            }
        }

        while (!operators.isEmpty()) {
            this.expression.add(operators.pop());
        }
    }

    void print_expression() {
        for (String entity : expression) {
            System.out.print(entity + " ");
        }
        System.out.println();
    }

    void evaluate() {
        Stack<Integer> stack = new Stack<>();
        for (String entity : expression) {
            if (precedence.get(entity) == null) {
                stack.add(Integer.parseInt(entity));
            } else {
                int operand2 = stack.pop();
                int operand1 = stack.pop();
                int result = switch (entity) {
                    case "+" -> operand1 + operand2;
                    case "-" -> operand1 - operand2;
                    case "*" -> operand1 * operand2;
                    case "/" -> operand1 / operand2;
                    default -> 0;
                };
                stack.add(result);
            }
        }
        System.out.println(stack.pop());
    }

    void clear() {
        expression.clear();
        operators.clear();
    }
}

public class Main {
    public static void main(String[] args) {
        RPN rpn = new RPN();
        rpn.format_expression("1 + 2 * 5 / 2 - 1");
        rpn.print_expression();
        rpn.evaluate();

        rpn.clear();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the expression: ");
        String expression = scanner.nextLine();
        rpn.format_expression(expression);
        rpn.print_expression();
        rpn.evaluate();
    }
}