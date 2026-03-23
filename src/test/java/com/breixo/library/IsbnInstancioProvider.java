package com.breixo.library;

import com.breixo.library.domain.vo.Isbn;

import org.instancio.generator.Generator;
import org.instancio.spi.InstancioServiceProvider;

/** The Class Isbn Instancio Provider. */
public class IsbnInstancioProvider implements InstancioServiceProvider {

    /** The Constant VALID_ISBN. */
    private static final String VALID_ISBN = "9780134685991";

    /** {@inheritDoc} */
    @Override
    public GeneratorProvider getGeneratorProvider() {
        return (node, generators) -> {
            if (node.getTargetClass() == Isbn.class) {
                return (Generator<Isbn>) random -> new Isbn(VALID_ISBN);
            }
            return null;
        };
    }
}
