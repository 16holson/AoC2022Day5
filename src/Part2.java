import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

public class Part2
{
    private static Stack<String> tempStack;
    public static void main(String[] args) throws IOException
    {
        ArrayList<String> commands = new ArrayList<>();
        ArrayList<Stack<String>> boxStack = new ArrayList<>();
        for(int i = 0; i < 9; i++)
        {
            boxStack.add(new Stack<>());
        }


        boolean top = false;
        String line;
        String file = new File("").getAbsolutePath();
        file = file.concat("\\src\\InputData.txt");
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));

        while((line = bufferedReader.readLine()) != null)
        {
            //Get the boxes chart
            if(!top)
            {
                //boxes
                if(line.contains("["))
                {
                    int position = 1;
                    for(int i = 1; i < line.length(); i = i + 4)
                    {

                        if(line.charAt(i) != ' ')
                        {
                            //Add to boxStack with position being the index
                            boxStack.get(position-1).push(String.valueOf(line.charAt(i)));
                        }
                        position += 1;
                    }
                }
                //No more boxes
                if(line.length() == 0)
                {
                    top = true;
                }
            }
            //Commands
            else
            {
                commands.add(String.valueOf(line.replaceAll("move (\\d*) from (\\d*) to (\\d*)", "$1-$2-$3")));
            }
        }


        for (Stack<String> boxes : boxStack)
        {
            tempStack = boxes;
            reverse();
            boxes = tempStack;
        }

        commands.forEach((command) ->{
            String split[] = command.split("-");
            ArrayList<String> boxClump = new ArrayList<>();
            for(int i = 0; i < Integer.parseInt(split[0]); i++)
            {
                boxClump.add(boxStack.get(Integer.parseInt(split[1])-1).pop());
            }
            Collections.reverse(boxClump);
            boxClump.forEach(box -> boxStack.get(Integer.parseInt(split[2])-1).push(box));

        });

        String message = "";
        for (Stack<String> boxes : boxStack)
        {
            message = message.concat(boxes.pop());
        }
        System.out.println(message);

    }

    static void reverse()
    {
        if(tempStack.size() > 0)
        {
            String x = tempStack.peek();
            tempStack.pop();
            reverse();
            insertAtBottom(x);
        }
    }
    static void insertAtBottom(String x)
    {
        if(tempStack.isEmpty())
        {
            tempStack.push(x);
        }
        else
        {
            String a = tempStack.peek();
            tempStack.pop();
            insertAtBottom(x);
            tempStack.push(a);
        }
    }
}
