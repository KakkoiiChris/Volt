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

function forEach(list, action) {
    for (x : list) {
        action(x);
    }
}

// Threads

function pause(seconds);

function run(handler);

function exit(code);