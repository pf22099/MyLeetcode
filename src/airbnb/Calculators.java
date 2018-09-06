package airbnb;

import java.util.*;

public class Calculators {

  /**
   * LC 277 CalculatorII
   * @param s
   * @return
   */
  public int calculate(String s) {
    if(s == null || s.length() == 0) {
      throw new RuntimeException("Invalid input");
    }
    Stack<Character> opStack = new Stack<>();
    Stack<Integer> numStack = new Stack<>();
    pushAll(s, opStack, numStack);
    // System.out.println(opStack);
    // System.out.println(numStack);
    calculate(opStack, numStack);
    return numStack.pop();
  }

  private void calculate(Stack<Character> opStack, Stack<Integer> numStack) {
    while(!opStack.isEmpty()) {
      calculateTop(opStack, numStack);
    }
  }

  private void pushAll(String s, Stack<Character> opStack, Stack<Integer> numStack) {
    for(int i = 0; i < s.length(); i++) {
      char c = s.charAt(i);
      // System.out.println(c);
      if(c == ' ') {
        continue;
      }
      if(c == '*' || c == '/') {
        while(!opStack.isEmpty() && (opStack.peek() == '*' || opStack.peek() == '/')) {
          calculateTop(opStack, numStack);
        }
        opStack.push(c);
      } else if(c == '+' || c == '-') {
        while(!opStack.isEmpty()) {
          calculateTop(opStack, numStack);
        }
        opStack.push(c);
      } else {
        int num = 0;
        while(i < s.length() && Character.isDigit(s.charAt(i))) {
          num = num * 10 + (s.charAt(i) - '0');
          i++;
        }
        i--; // because in the for loop i will be increased one more time
        numStack.push(num);
      }
    }
  }

  private void calculateTop(Stack<Character> opStack, Stack<Integer> numStack) {
    int num2 = numStack.pop();
    int num1 = numStack.pop();
    char op = opStack.pop();
    int res = operate(num1, num2, op);
    numStack.push(res);
  }

  private int operate(int num1, int num2, char c) {
    switch(c) {
      case '+':
        return num1 + num2;
      case '-':
        return num1 - num2;
      case '*':
        return num1 * num2;
      case '/':
        return num1 / num2;
      default:
        throw new RuntimeException("Invalid operation!");
    }
  }
}
