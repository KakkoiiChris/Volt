/*********************************************
 * :::     :::  ::::::::  :::    ::::::::::: *
 * :+:     :+: :+:    :+: :+:        :+:     *
 * +:+     +:+ +:+    +:+ +:+        +:+     *
 * +#+     +:+ +#+    +:+ +#+        +#+     *
 *  +#+   +#+  +#+    +#+ +#+        +#+     *
 *   #+#+#+#   #+#    #+# #+#        #+#     *
 *     ###      ########  ########## ###     *
 *                                           *
 *       Lightweight Scripting Language      *
 *********************************************/

// Console IO

function read;

function write(x);

// Time

function time;

// Functional

function identity(x) -> x;

// Lists

function add(list, element);

function forEach(list, action) {
    for (x : list) {
        action(x);
    }
}

function map(list, transform) {
    new = [];
    
    for (x : list) {
        add(new, transform(x));
    }
    
    return new;
}

// Threads

function pause(seconds);

function run(handler);

function exit(code);