import java.util.*;
public class VM {
    private Scanner sc = new Scanner(System.in);
    private final int LENGTH = 65535;
    private byte[] mem = new byte[LENGTH];
    private int dataPointer;
    public Stack<Integer> stack = new Stack<Integer>();
    public void interpret() {
    	evaluate(sc.nextLine());
    }
    public void interpret(String code) {
       evaluate(code);
    }
    private void evaluate(String code)
    {
        ArrayList<String> functions = new ArrayList<String>();
    	String rest = "";
    	for(int i = 0; i < code.length(); i++) {
    		String function = "";
    		
    		if(code.charAt(i) == '{')
    		{
    			for (i = i + 1; i < code.length(); i++) {
					if(code.charAt(i) == '}')
					{
						break;
					}
					else
					{
						function += code.charAt(i);
					}
				}
    			if(!function.equals(""))functions.add(function);
    			function = "";
    		}
    		else if(code.charAt(i) != '}')
    		{
    			rest += code.charAt(i);
    		}
    	}
    	code = rest;
    	 int l = 0;
         for(int i = 0; i < code.length(); i++) {
         	String num = "";
         	if((""+code.charAt(i)).matches("\\d")) {

                 for(i = 0; i < code.length(); i++) {
         			if((""+code.charAt(i)).matches("\\d"))num += code.charAt(i);
         		}
         		stack.push(Integer.parseInt(num));
         		i -= 2;
         		
         	} else if(code.charAt(i) == '>') {
                 dataPointer = (dataPointer == LENGTH-1) ? 0 : dataPointer + 1;
             } else if(code.charAt(i) == '<') {
                 dataPointer = (dataPointer == 0) ? LENGTH-1 : dataPointer - 1;
             } else if(code.charAt(i) == '+') {
                 mem[dataPointer]++;
             } else if(code.charAt(i) == '-') {
                 mem[dataPointer]--;
             } else if(code.charAt(i) == '.') {
                 System.out.print((char) mem[dataPointer]);
             } else if(code.charAt(i) == ',') {
                 mem[dataPointer] = (byte) sc.next().charAt(0);
             } else if(code.charAt(i) == ':'){
             	stack.push((int) mem[dataPointer]);
             } else if(code.charAt(i) == ';'){
             	mem[dataPointer] = (byte) stack.pop().intValue();
             } else if(code.charAt(i) == '_'){
             	stack.pop();
             } else if(code.charAt(i) == '='){
             	stack.push(stack.peek());
             } else if(code.charAt(i) == '@') {
            	 stack.push(dataPointer);
             } else if(code.charAt(i) == '*') {
            	 dataPointer = stack.pop();
             } else if(code.charAt(i) == '!') {
            	 if(mem[dataPointer] == 0)
            	 {
            		 mem[dataPointer] = 1;
            	 }
            	 else
            	 {
            		 mem[dataPointer] = 0;
            	 }
             } else if(code.charAt(i) == '&') {
            	 if(mem[dataPointer] == 0 && stack.pop() == 0)
            	 {
            		 mem[dataPointer] = 1;
            	 }
            	 else
            	 {
            		 mem[dataPointer] = 0;
            	 }
             } else if(code.charAt(i) == '|') {
            	 if(mem[dataPointer] == 0 || stack.pop() == 0)
            	 {
            		 mem[dataPointer] = 1;
            	 }
            	 else
            	 {
            		 mem[dataPointer] = 0;
            	 }
             } else if(code.charAt(i) == '^') {
            	 mem[dataPointer] = (byte) (mem[dataPointer] * 2);
             } else if(code.charAt(i) == '/') {
            	 mem[dataPointer] = (byte) (mem[dataPointer] / 2);
             } else if(code.charAt(i) == '#') {
            	 stack.push(stack.size());
             } else if(code.charAt(i) == '~') {
            	 stack.push(new Random().nextInt(1));
             } else if(code.charAt(i) == '\"') {
            	 mem[dataPointer] += stack.pop();
             } else if(code.charAt(i) == '\'') {
            	 mem[dataPointer] -= stack.pop();
             } else if(code.charAt(i) == '$') {
            	 evaluate(functions.get(stack.pop()));
             } else if(code.charAt(i) == '[') {
            	 
                 if(mem[dataPointer] == 0) {
                     i++;
                     while(l > 0 || code.charAt(i) != ']') {
                         if(code.charAt(i) == '[') l++;
                         if(code.charAt(i) == ']') l--;
                         i++;
                     }
                 }
             } else if(code.charAt(i) == ']') {
                 if(mem[dataPointer] != 0) {
                     i--;
                     while(l > 0 || code.charAt(i) != '[') {
                         if(code.charAt(i) == ']') l++;
                         if(code.charAt(i) == '[') l--;
                         i--;
                     }
                     i--;
                 }
             }
         }
    }
    public static void main(String[] args) {
        new VM().interpret("{+++.}0$");
        
    }
}