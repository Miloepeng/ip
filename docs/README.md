# Ego Chatbot User Guide

![Ego Chatbot GUI](./Ui.png)

Ego Chatbot is a user-friendly chatbot designed to help you
organize and manage your tasks efficiently. Whether you're keeping
track of your to-dos, assignments or events, Ego has got your back!

## Quick Start
1. Ensure you have JDK `17` or above installed in your computer
2. Download the latest `.jar` file from [here](https://github.com/Miloepeng/ip/releases)
3. Copy the file to the folder you want to use as the home folder for your chatbot
4. Open the command terminal, `cd` into the folder you put the jar file in, and use the
`java -jar ego.jar` command to run the application
5. Type the command in the command box below and press Send to execute it e.g. typing 
`help` and pressing Send will return the list of commands available

## Features
### Notes about the command format:
1. Words in `<>` are parameters to be supplied by the user
2. Parameters must be in the correct order
3. Extraneous parameters for commands that do not take in parameters (such as `help`,
`list`, `bye`) will be ignored
e.g. if the commmand specifies `help 123`, it will be interpreted as `help`
### Seeing list of commands available: `help`
Shows a message explaining how to utilize the different commands available  
Example: `help`  
Expected Output: `Hello there diamonds in the rough, feeling lost?  
            Here are the list of commands you can use:`

            - help
                Show list of commands
            - list
                Show current task you have
            - mark <taskNum>
                Mark a task as completed
            - unmark <taskNum>
                Mark a task as not done
            - todo <desc>
                Add a todo task with description
            - deadline <desc> /by <deadline>
                Add a task with a deadline
            - event <desc> /from <start> /to <end>
                Add an event with start and end date
            - delete <taskNum>
                Delete a task
            - find <keyword>
                Find tasks by keyword
            - bye
                Exit and save tasks`

### Adding a Todo task: `todo`
Format: `todo <desc>`  
Example: `todo buy shirt`  
Expected output:
```
Added: [T][]buy shirt
Now you have 1 task to complete! Keep it up egoist!
```

## Adding deadlines: `deadline`
Format: `deadline <desc> /by <deadline`  
Example: `deadline buy pants /by 2025-09-13`  
Expected output:
```
Added: [D][]buy pants(by: Sep 13 2025)
Now you have 2 tasks to complete! Keep it up egoist!
```

### Adding events: `event`
Format: `event <desc> /from <start> /to <end>`  
Example: `event japan /from 2025-09-13 /to 2025-09-20`  
Expected output:
```
Added: [E][]japan(from: Sep 13 2025 to: Sep 20 2025)
Now you have 3 tasks to complete! Keep it up egoist!
```

### Mark task as completed: `mark`
Format: `mark <taskNum>`  
Example: `mark 1`  
Expected output:
```
Well done egoist, I've marked this task as completed:
[T][X]buy shirt
```

### Mark task as incomplete: `unmark`
Format: `unmark <taskNum>`  
Example: `unmark 1`  
Expected output:
```
Alright... I'll mark this task as not done yet since
you have yet to grow your ego...
[T][]buy shirt
```

### Delete a task: `delete`
Format: `delete <taskNum>`  
Example: `delete 2`  
Expected output:
```
Roger, I'll delete this task from your list egoist!
    [D][]buy pants(by: Sep 13 2025)
Now you have 2 tasks to complete!
```

### Find specific tasks by keyword: `find`
Format: `find <keyword>`  
Example: `find shirt`  
Expected output:
```
Here are the relevant tasks you asked for:
1.[T][]buy shirt
```

### List out the current tasks: `list`
Example: `list`  
Expected output:  
```
OK egoist, ready to rock your to-do list?
1.[T][]buy shirt
2.[E][]japan(from: Sep 13 2025 to: Sep 20 2025)
```

### Exit the chatbot: `bye`
Example: `bye`  
Expected output:
```
Farewell egoist... see you soon!
```

## Saving the data
Data is saved in the hard disk automatically after closing
the GUI or using the `bye` command

## Hope you enjoy using Ego chatbot!