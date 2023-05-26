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

// Lists

function add(list, element);

function addAt(list, index, element);

function remove(list, element);

function removeAt(list, index);

function forEach(list, action) {
    for (x : list) {
        x::action();
    }
}

function filter(list, predicate) {
    new = [];

    for (x : list) {
        if (x::predicate()) {
            new::add(x);
        }
    }

    return new;
}

function map(list, transform) {
    new = [];

    for (x : list) {
        new::add(x::transform());
    }

    return new;
}

function all(list, predicate) {
    for (x : list) {
        if (not x::predicate()) {
            return false;
        }
    }

    return true;
}

function any(list, predicate) {
    for (x : list) {
        if (x::predicate()) {
            return true;
        }
    }

    return false;
}

function none(list, predicate) -> not list::any(predicate);

// Console IO

function read;

function write(x);

// Time

function time;

// Functional

function identity(x) -> x;

// Threads

function pause(seconds);

function run(handler);

function exit(code);