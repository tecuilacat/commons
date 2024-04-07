package com.github.tecuilacat.rfs.parser;

import com.github.tecuilacat.rfs.nav.Position;

/**
 * Parses a string (make sure only the needed parts are in that string divided by ';')
 */
public final class PositionParser implements Parser<String, Position> {
    
    @Override
    public Position parse(String positionAsString) {
        String[] elements = positionAsString.split(";"); //for parsing
        return new Position(
                Double.parseDouble(elements[1]),
                Double.parseDouble(elements[3]),
                Double.parseDouble(elements[5]),
                Double.parseDouble(elements[7]),
                Double.parseDouble(elements[9]),
                Double.parseDouble(elements[11])
        );
    }

}
