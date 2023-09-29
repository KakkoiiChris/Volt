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
 
// Constants
 
MIN_NUMBER        = 4.9e-324;
MAX_VALUE         = 1.7976931348623157e308;
POSITIVE_INFINITY = 1.0 / 0.0;
NEGATIVE_INFINITY = -1.0 / 0.0;
NaN               = -(0.0 / 0.0);

PI  = 3.14159265358979323846;
TAU = PI * 2;
E   = 2.7182818284590452;

// Functions

function sin(n);

function cos(n);

function tan(n);

function asin(n);

function acos(n);

function atan(n);

function atan2(y, x);

function sinh(n);

function cosh(n);

function tanh(n);

function asinh(n);

function acosh(n);

function atanh(n);

function hypot(x, y);

function sqrt(n);

function cbrt(n);

function pow(n, exponent);

function exp(n);

function expm1(n);

function log(n, base);

function ln(n);

function log10(n);

function log2(n);

function ln1p(n);

function ceil(n);

function floor(n);

function truncate(n);

function round(n);

function abs(n);

function min(a, b);

function max(a, b);

function sign(n);

function withSign(n, sign);

function ulp(n);

function nextUp(n);

function nextDown(n);

function nextTowards(n, to);

function isNaN(n);

function isFinite(n);

function isInfinite(n) -> not isFinite(n);

function fact(n);

function comb(n, k);

function perm(n, k);

function sum(list);

function prod(list);

function random;