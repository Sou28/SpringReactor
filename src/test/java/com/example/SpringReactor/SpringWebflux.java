package com.example.SpringReactor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class SpringWebflux {

    @Test
    public void simpleWebFlux(){
        Flux<String> fluxColors = Flux.just("Spring","Autum","Winter","SUmmer");

        fluxColors.log().subscribe(System.out::println);

        System.out.println("Using BaseSubscriber " );

        fluxColors.log().subscribe(new BaseSubscriber<String>() {
            @Override
            public void hookOnSubscribe(Subscription subscription) {
                //request(2); //Only Execute Spring And Autun
                requestUnbounded();
            }

            @Override
            public void hookOnNext(String str) {
                System.out.println("Cancelling after having received " + str);
                //cancel();
            }
        }); 

        //TODO Flux to mono 
        Mono<String> monoColours=fluxColors.log().next();
        System.out.println("Printing mono--"+monoColours.block()); 
        monoColours.log().subscribe(x-> System.out.println("Printing mono block "+x));

        fluxColors.log().doOnRequest(x->System.out.println("Printing request ===>"+ x))
                  .subscribe(System.out::println);

        fluxColors.toIterable().forEach(x -> System.out.println("Printing for each === "+x));

        

    }

    @Test
    public void verifyFluxToMonoApis(){
        Flux.just(Arrays.asList(1, 2, 3), Arrays.asList(4, 5, 6), Arrays.asList(7, 8, 9, 10))
            .flatMap(integers -> Mono.just(integers))
            .doOnNext(System.out::println)
             .blockLast();
        Flux.just(Arrays.asList(1, 2, 3), Arrays.asList(4, 5, 6), Arrays.asList(7, 8, 9, 10))
             .flatMap(integers -> filterAndMap( Mono.just(integers)))
             .doOnNext(System.out::println)
              .blockLast();
    }


    private Mono<List<Integer>> filterAndMap(Mono<List<Integer>> listMono) {
        return listMono.filter(integers -> integers.size() == 3)
            .map(integers -> integers.stream()
                .map(integer -> integer * 10)
                .collect(Collectors.toList()));
      }

    @Test
    public void verifyFluxToMonoApis1(){
        // Flux.just("Sougata", "Majumdar", "Deba").log()
        // .flatMap(integers -> Mono.just(integers).log())
        // .doOnNext(System.out::println)
        //  .blockLast();

         Flux.just("Sougata", "Majumdar", "Deba").log()
         .flatMap(x -> Mono.just(x).log())
         .subscribe(System.out::println);
         
    }  

    @Test
    public void verySimpleWebFlux(){
        Flux<A> fluxSimple = Flux.just(new A(1,2),new A(2,3),new A(3,4),new A(4,5));
        int sum1=fluxSimple.toStream().reduce(0, (sum,obj) -> sum+obj.display()+obj.display(),Integer::sum);
        System.out.println(sum1);

        List<Integer> array = Arrays.asList(-2, 0, 4, 6, 8);
  
        // Finding sum of all elements
        int sum = array.stream().reduce(0,(element1, element2) -> element1 + element2);

        
    }


    
    @Test
    public void onErrorExample() {
        Flux<String> fluxCalc = Flux.just(-1, 0, 1).map(i -> "10 / " + i + " = " + (10 / i));
        fluxCalc.subscribe(value -> System.out.println("Next: " + value));

    }

    @Test
    public void convertListToMap(){
        List<Student> lt = new ArrayList<>();
  
        // add the member of list
        lt.add(new Student(1, "Geeks"));
        lt.add(new Student(2, "For"));
        lt.add(new Student(3, "Geeks"));
  
        // create map with the help of
        // Collectors.toMap() method
        Map<Integer, String>
            map = lt.stream()
                      .collect(
                          Collectors
                              .toMap(
                                  Student::getId,
                                  Student::getName,
                                  (x,y)->x+"afasdflsadf "+y, LinkedHashMap::new));
  
        System.out.println( map);

        Map<String,String> map1 = lt.stream().collect(Collectors.toMap(
                                                x-> x.getId().toString(),x-> x.getName(),(s,a)->s + ", " + a, LinkedHashMap::new)); 

      System.out.println("Helloo "+ map1);
        // List<Classic> list = new ArrayList<>();
  
        // // add the member of list
        // list.add(new Classic("1", "Geeks"));
        // list.add(new Classic("2", "For"));
        // list.add(new Classic("3", "Geeks"));


        // Map<String,String> mapper = list.stream().collect(Collectors.toMap(Classic::getId, Classic::getName,));

        // print map
        
    }
 
}
