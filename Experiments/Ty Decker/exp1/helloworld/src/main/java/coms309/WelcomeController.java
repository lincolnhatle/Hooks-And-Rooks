package coms309;

import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
class WelcomeController {

    private int[] array1 = new int[5];
    private int arraySize;

    @GetMapping("/")
    public String welcome() {
        return "Array: " + Arrays.toString(array1);
    }

    @GetMapping("/{num}")
    public String welcome(@PathVariable int num) {

        return "Item " + num + " of array: " + array1[num];
    }

    @PostMapping("/")
    public String post1() {return "Enter an integer";}

    @PostMapping("/{post1}")
    public String post(@PathVariable int post1) {

        System.out.println(post1);

        array1[arraySize] = post1;
        arraySize++;

        return "int saved\nNew Array: " + Arrays.toString(array1);

    }

    @DeleteMapping("/")
    public String delete() {
        if (arraySize < 1) {
            return "Array empty, no deletion";
        }
        array1[arraySize - 1] = 0;
        arraySize--;

        return "Array element deleted\n New Array: " + Arrays.toString(array1);
    }

    @DeleteMapping("/{anything}")
    public String badDelete() {return "Path must be empty for deletion";}

    @PutMapping("/{index}") // Sets array index to 1
    public String put(@PathVariable int index) {
        array1[index] = 1;
        arraySize = index + 1;

        return "Array element changed to 1\nNew Array: " + Arrays.toString(array1);
    }


}
