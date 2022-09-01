package toste.proj.vue.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import toste.proj.vue.model.Friend;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/twitter")
public class FriendsController{

    private List<Friend> friends;

    public FriendsController(){
        friends = new ArrayList<>();
        friends.add(new Friend("adb", "cod"));
        friends.add(new Friend("adb2", "cod2"));
        friends.add(new Friend("adb3", "cod3"));
        friends.add(new Friend("adb4", "cod4"));
        friends.add(new Friend("adb5", "cod5"));

    }

    @GetMapping("/")
    public List<Friend> list(){
        return friends;
    }

    @GetMapping("/{id}")
    public Friend get(@PathVariable String id){
        return friends.stream().filter(f -> id.equals(f.getId())).findAny().orElse(null);
    }
}
