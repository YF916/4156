package com.example.demotest.controller;

import com.example.demotest.model.DispatchHistory;
import com.example.demotest.model.Responder;
import com.example.demotest.repository.ResponderRepository;
import com.example.demotest.repository.DispatchHistoryRepository;
import com.example.demotest.repository.ResponderRepository;
import com.example.demotest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


@Controller
@RequestMapping(path="/responder")
public class DispatchController {
    @Autowired
    private ResponderRepository responderRepository;
    @Autowired
    private DispatchHistoryRepository dispatchHistoryRepository;
    @Autowired
    private UserRepository userRepository;

    @PostMapping(path="/add")
    public @ResponseBody
    String addNewResponder (Responder responder) {
        responderRepository.save(responder);
        return "Saved";
    }

    @GetMapping(path="/search")
    public @ResponseBody Iterable<Responder> getAllResponders() {
        // This returns a JSON or XML with the users
        /*double centerLatitude = 40.7128;
        double centerLongitude = 74.0060;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);
        QueryBuilder builder = fullTextSession.getSearchFactory()
                .buildQueryBuilder().forEntity( Responder.class ).get();

        org.apache.lucene.search.Query luceneQuery = builder
                .spatial()
                .within(2, Unit.KM )
                .ofLatitude( centerLatitude )
                .andLongitude( centerLongitude )
                .createQuery();

        org.hibernate.Query hibQuery = fullTextSession
                .createFullTextQuery( luceneQuery, Responder.class );
        List results = hibQuery.list();*/
        return responderRepository.findAll();
    }
    @PostMapping(path="/dispatch") // Map ONLY POST Requests
    public @ResponseBody String dispatchResponder (@RequestParam Integer id,
                                                   @RequestParam Double latitude,@RequestParam Double longitude) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request

        Responder responderToDispatch = responderRepository.getReferenceById(id);
        responderToDispatch.setStatus("Dispatched");
        responderToDispatch.setLongitude(longitude);
        responderToDispatch.setLatitude(latitude);
        responderRepository.save(responderToDispatch);
        return "Dispatched";
    }

    @GetMapping(path="/recommend/rate") // called when the dispatch starts
    public @ResponseBody
    Responder getRateRecommend (@RequestParam("user_id") Integer id) {
        Iterable<DispatchHistory> allHistory = dispatchHistoryRepository.findByCaller(userRepository.getReferenceById(id));
        Responder responder = null;
        int maxRate = -1;
        for (DispatchHistory history: allHistory) {
            if (history.getRating() > maxRate) {
                maxRate = history.getRating();
                responder = history.getResponder();
            }
        }
        return responder;
    }

    @GetMapping(path="/recommend/frequency") // called when the dispatch starts
    public @ResponseBody
    Responder getFreqRecommend (@RequestParam("user_id") Integer id) {
        Iterable<DispatchHistory> allHistory = dispatchHistoryRepository.findByCaller(userRepository.getReferenceById(id));
        ArrayList<Responder> responders = new ArrayList<>();
        for (DispatchHistory history: allHistory) {
            responders.add(history.getResponder());
        }
        Map<Responder, Integer> countMap = new HashMap<>();
        for(Responder r: responders)
        {
            Integer count = countMap.get(r);
            if(count == null) {
                count = 0;
            }
            count++;
            countMap.put(r, count);
        }

        Map.Entry<Responder, Integer> mostRepeated = null;
        for(Map.Entry<Responder, Integer> e: countMap.entrySet())
        {
            if(mostRepeated == null || mostRepeated.getValue() < e.getValue())
                mostRepeated = e;
        }
        try {
            return mostRepeated.getKey();
        } catch (NullPointerException e) {
            return null;
        }
    }
}
