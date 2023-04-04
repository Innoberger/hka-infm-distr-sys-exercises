package de.hska.iwi.vislab.lab1.example.ws;

import javax.jws.WebService;

/** Dienstimplementierung */
@WebService(endpointInterface = "de.hska.iwi.vislab.lab1.example.ws.FibonacciService")
public class FibonacciServiceImpl implements FibonacciService {

    @Override
    public int getFibonacci(int n) {
        if (n < 1)
            throw new IllegalArgumentException(String.format("n must greater than 0, got %s", n));

        if (n < 3)
            return 1;

        return getFibonacci(n - 1) + getFibonacci(n - 2);
    }

}
