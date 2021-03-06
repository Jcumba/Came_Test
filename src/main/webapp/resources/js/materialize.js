/*!
 * Materialize v1.0.0 (http://materializecss.com)
 * Copyright 2014-2017 Materialize
 * MIT License (https://raw.githubusercontent.com/Dogfalo/materialize/master/LICENSE)
 */
var _get = function get(object, property, receiver) { if (object === null) object = Function.prototype; var desc = Object.getOwnPropertyDescriptor(object, property); if (desc === undefined) { var parent = Object.getPrototypeOf(object); if (parent === null) { return undefined; } else { return get(parent, property, receiver); } } else if ("value" in desc) { return desc.value; } else { var getter = desc.get; if (getter === undefined) { return undefined; } return getter.call(receiver); } };
var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();
function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

/*! cash-dom 1.3.5, https://github.com/kenwheeler/cash @license MIT */
(function (factory) {
    window.cash = factory();
})(function () {
    var doc = document,
        win = window,
        ArrayProto = Array.prototype,
        slice = ArrayProto.slice,
        filter = ArrayProto.filter,
        push = ArrayProto.push;
    var noop = function () { },
        isFunction = function (item) {
            // @see https://crbug.com/568448
            return typeof item === typeof noop && item.call;
        },
        isString = function (item) {
            return typeof item === typeof "";
        };
    var idMatch = /^#[\w-]*$/,
        classMatch = /^\.[\w-]*$/,
        htmlMatch = /<.+>/,
        singlet = /^\w+$/;
    function find(selector, context) {
        context = context || doc;
        var elems = classMatch.test(selector) ? context.getElementsByClassName(selector.slice(1)) : singlet.test(selector) ? context.getElementsByTagName(selector) : context.querySelectorAll(selector);
        return elems;
    }

    var frag;
    function parseHTML(str) {
        if (!frag) {
            frag = doc.implementation.createHTMLDocument(null);
            var base = frag.createElement("base");
            base.href = doc.location.href;
            frag.head.appendChild(base);
        }

        frag.body.innerHTML = str;
        return frag.body.childNodes;
    }



    function Init(selector, context) {
        if (!selector) {
            return this;
        }



        var elems = selector,
            i = 0,
            length;
        if (isString(selector)) {
            elems = idMatch.test(selector) ?
                // If an ID use the faster getElementById check
                doc.getElementById(selector.slice(1)) : htmlMatch.test(selector) ?
                    // If HTML, parse it into real elements
                    parseHTML(selector) :
                    // else use `find`
                    find(selector, context);
            // If function, use as shortcut for DOM ready
        } else if (isFunction(selector)) {
            onReady(selector); return this;
        }

        if (!elems) {
            return this;
        }

        // If a single DOM element is passed in or received via ID, return the single element
        if (elems.nodeType || elems === win) {
            this[0] = elems;
            this.length = 1;
        } else {
            // Treat like an array and loop through each item.
            length = this.length = elems.length;
            for (; i < length; i++) {
                this[i] = elems[i];
            }
        }

        return this;
    }

    function cash(selector, context) {
        return new Init(selector, context);
    }

    var fn = cash.fn = cash.prototype = Init.prototype = { // jshint ignore:line
        cash: true,
        length: 0,
        push: push,
        splice: ArrayProto.splice,
        map: ArrayProto.map,
        init: Init
    };
    Object.defineProperty(fn, "constructor", { value: cash });
    cash.parseHTML = parseHTML;
    cash.noop = noop;
    cash.isFunction = isFunction;
    cash.isString = isString;
    cash.extend = fn.extend = function (target) {
        target = target || {};
        var args = slice.call(arguments),
            length = args.length,
            i = 1;
        if (args.length === 1) {
            target = this;
            i = 0;
        }

        for (; i < length; i++) {
            if (!args[i]) {
                continue;
            }
            for (var key in args[i]) {
                if (args[i].hasOwnProperty(key)) {
                    target[key] = args[i][key];
                }
            }
        }

        return target;
    };
    function each(collection, callback) {
        var l = collection.length,
            i = 0;
        for (; i < l; i++) {
            if (callback.call(collection[i], collection[i], i, collection) === false) {
                break;
            }
        }
    }

    function matches(el, selector) {
        var m = el && (el.matches || el.webkitMatchesSelector || el.mozMatchesSelector || el.msMatchesSelector || el.oMatchesSelector);
        return !!m && m.call(el, selector);
    }

    function getCompareFunction(selector) {
        return (
            /* Use browser's `matches` function if string */
            isString(selector) ? matches :
                /* Match a cash element */
                selector.cash ? function (el) {
                    return selector.is(el);
                } :
                    /* Direct comparison */
                    function (el, selector) {
                        return el === selector;
                    }
        );
    }

    function unique(collection) {
        return cash(slice.call(collection).filter(function (item, index, self) {
            return self.indexOf(item) === index;
        }));
    }

    fn.extend({
        addClass: function (c) {
            var classes = getClasses(c);
            return classes ? this.each(function (v) {
                var spacedName = " " + v.className + " ";
                each(classes, function (c) {
                    addClass(v, c, spacedName);
                });
            }) : this;
        },
        attr: function (name, value) {
            if (!name) {
                return undefined;
            }

            if (isString(name)) {
                if (value === undefined) {
                    return this[0] ? this[0].getAttribute ? this[0].getAttribute(name) : this[0][name] : undefined;
                }

                return this.each(function (v) {
                    if (v.setAttribute) {
                        v.setAttribute(name, value);
                    } else {
                        v[name] = value;
                    }
                });
            }

            for (var key in name) {
                this.attr(key, name[key]);
            }

            return this;
        },
        hasClass: function (c) {
            var check = false,
                classes = getClasses(c);
            if (classes && classes.length) {
                this.each(function (v) {
                    check = hasClass(v, classes[0]);
                    return !check;
                });
            }
            return check;
        },
        prop: function (name, value) {
            if (isString(name)) {
                return value === undefined ? this[0][name] : this.each(function (v) {
                    v[name] = value;
                });
            }

            for (var key in name) {
                this.prop(key, name[key]);
            }

            return this;
        },
        removeAttr: function (name) {
            return this.each(function (v) {
                if (v.removeAttribute) {
                    v.removeAttribute(name);
                } else {
                    delete v[name];
                }
            });
        },
        removeClass: function (c) {
            if (!arguments.length) {
                return this.attr("class", "");
            }
            var classes = getClasses(c);
            return classes ? this.each(function (v) {
                each(classes, function (c) {
                    removeClass(v, c);
                });
            }) : this;
        },
        removeProp: function (name) {
            return this.each(function (v) {
                delete v[name];
            });
        },
        toggleClass: function (c, state) {
            if (state !== undefined) {
                return this[state ? "addClass" : "removeClass"](c);
            }
            var classes = getClasses(c);
            return classes ? this.each(function (v) {
                var spacedName = " " + v.className + " ";
                each(classes, function (c) {
                    if (hasClass(v, c)) {
                        removeClass(v, c);
                    } else {
                        addClass(v, c, spacedName);
                    }
                });
            }) : this;
        }
    });
    fn.extend({
        add: function (selector, context) {
            return unique(cash.merge(this, cash(selector, context)));
        },
        each: function (callback) {
            each(this, callback);
            return this;
        },
        eq: function (index) {
            return cash(this.get(index));
        },
        filter: function (selector) {
            if (!selector) {
                return this;
            }

            var comparator = isFunction(selector) ? selector : getCompareFunction(selector);
            return cash(filter.call(this, function (e) {
                return comparator(e, selector);
            }));
        },
        first: function () {
            return this.eq(0);
        },
        get: function (index) {
            if (index === undefined) {
                return slice.call(this);
            }
            return index < 0 ? this[index + this.length] : this[index];
        },
        index: function (elem) {
            var child = elem ? cash(elem)[0] : this[0],
                collection = elem ? this : cash(child).parent().children();
            return slice.call(collection).indexOf(child);
        },
        last: function () {
            return this.eq(- 1);
        }

    });
    var camelCase = function () {
        var camelRegex = /(?:^\w|[A-Z]|\b\w)/g,
            whiteSpace = /[\s-_]+/g;
        return function (str) {
            return str.replace(camelRegex, function (letter, index) {
                return letter[index === 0 ? "toLowerCase" : "toUpperCase"]();
            }).replace(whiteSpace, "");
        };
    }();
    var getPrefixedProp = function () {
        var cache = {},
            doc = document,
            div = doc.createElement("div"),
            style = div.style;
        return function (prop) {
            prop = camelCase(prop);
            if (cache[prop]) {
                return cache[prop];
            }

            var ucProp = prop.charAt(0).toUpperCase() + prop.slice(1),
                prefixes = ["webkit", "moz", "ms", "o"],
                props = (prop + " " + prefixes.join(ucProp + " ") + ucProp).split(" ");
            each(props, function (p) {
                if (p in style) {
                    cache[p] = prop = cache[prop] = p;
                    return false;
                }
            });
            return cache[prop];
        };
    }();
    cash.prefixedProp = getPrefixedProp;
    cash.camelCase = camelCase;
    fn.extend({
        css: function (prop, value) {
            if (isString(prop)) {
                prop = getPrefixedProp(prop);
                return arguments.length > 1 ? this.each(function (v) {
                    return v.style[prop] = value;
                }) : win.getComputedStyle(this[0])[prop];
            }

            for (var key in prop) {
                this.css(key, prop[key]);
            }

            return this;
        }

    });
    fn.extend({
        after: function (selector) {
            cash(selector).insertAfter(this);
            return this;
        },
        append: function (content) {
            insertContent(this, content);
            return this;
        },
        appendTo: function (parent) {
            insertContent(cash(parent), this);
            return this;
        },
        before: function (selector) {
            cash(selector).insertBefore(this);
            return this;
        },
        clone: function () {
            return cash(this.map(function (v) {
                return v.cloneNode(true);
            }));
        },
        empty: function () {
            this.html("");
            return this;
        },
        html: function (content) {
            if (content === undefined) {
                return this[0].innerHTML;
            }
            var source = content.nodeType ? content[0].outerHTML : content;
            return this.each(function (v) {
                return v.innerHTML = source;
            });
        },
        insertAfter: function (selector) {
            var _this = this;
            cash(selector).each(function (el, i) {
                var parent = el.parentNode,
                    sibling = el.nextSibling;
                _this.each(function (v) {
                    parent.insertBefore(i === 0 ? v : v.cloneNode(true), sibling);
                });
            });
            return this;
        },
        insertBefore: function (selector) {
            var _this2 = this;
            cash(selector).each(function (el, i) {
                var parent = el.parentNode;
                _this2.each(function (v) {
                    parent.insertBefore(i === 0 ? v : v.cloneNode(true), el);
                });
            });
            return this;
        },
        prepend: function (content) {
            insertContent(this, content, true);
            return this;
        },
        prependTo: function (parent) {
            insertContent(cash(parent), this, true);
            return this;
        },
        remove: function () {
            return this.each(function (v) {
                if (!!v.parentNode) {
                    return v.parentNode.removeChild(v);
                }
            });
        },
        text: function (content) {
            if (content === undefined) {
                return this[0].textContent;
            }
            return this.each(function (v) {
                return v.textContent = content;
            });
        }

    });
    var docEl = doc.documentElement;
    fn.extend({
        position: function () {
            var el = this[0];
            return {
                left: el.offsetLeft,
                top: el.offsetTop
            };
        },
        offset: function () {
            var rect = this[0].getBoundingClientRect();
            return {
                top: rect.top + win.pageYOffset - docEl.clientTop,
                left: rect.left + win.pageXOffset - docEl.clientLeft
            };
        },
        offsetParent: function () {
            return cash(this[0].offsetParent);
        }

    });
    fn.extend({
        children: function (selector) {
            var elems = [];
            this.each(function (el) {
                push.apply(elems, el.children);
            });
            elems = unique(elems);
            return !selector ? elems : elems.filter(function (v) {
                return matches(v, selector);
            });
        },
        closest: function (selector) {
            if (!selector || this.length < 1) {
                return cash();
            }
            if (this.is(selector)) {
                return this.filter(selector);
            }
            return this.parent().closest(selector);
        },
        is: function (selector) {
            if (!selector) {
                return false;
            }

            var match = false,
                comparator = getCompareFunction(selector);
            this.each(function (el) {
                match = comparator(el, selector);
                return !match;
            });
            return match;
        },
        find: function (selector) {
            if (!selector || selector.nodeType) {
                return cash(selector && this.has(selector).length ? selector : null);
            }

            var elems = [];
            this.each(function (el) {
                push.apply(elems, find(selector, el));
            });
            return unique(elems);
        },
        has: function (selector) {
            var comparator = isString(selector) ? function (el) {
                return find(selector, el).length !== 0;
            } : function (el) {
                return el.contains(selector);
            };
            return this.filter(comparator);
        },
        next: function () {
            return cash(this[0].nextElementSibling);
        },
        not: function (selector) {
            if (!selector) {
                return this;
            }

            var comparator = getCompareFunction(selector);
            return this.filter(function (el) {
                return !comparator(el, selector);
            });
        },
        parent: function () {
            var result = [];
            this.each(function (item) {
                if (item && item.parentNode) {
                    result.push(item.parentNode);
                }
            });
            return unique(result);
        },
        parents: function (selector) {
            var last,
                result = [];
            this.each(function (item) {
                last = item;
                while (last && last.parentNode && last !== doc.body.parentNode) {
                    last = last.parentNode;
                    if (!selector || selector && matches(last, selector)) {
                        result.push(last);
                    }
                }
            });
            return unique(result);
        },
        prev: function () {
            return cash(this[0].previousElementSibling);
        },
        siblings: function (selector) {
            var collection = this.parent().children(selector),
                el = this[0];
            return collection.filter(function (i) {
                return i !== el;
            });
        }

    });
    return cash;
});
;
var Component = function () {
    /**
     * Generic constructor for all components
     * @constructor
     * @param {Element} el
     * @param {Object} options
     */
    function Component(classDef, el, options) {
        _classCallCheck(this, Component);
        // Display error if el is valid HTML Element
        if (!(el instanceof Element)) {
            console.error(Error(el + ' is not an HTML Element'));
        }

        // If exists, destroy and reinitialize in child
        var ins = classDef.getInstance(el);
        if (!!ins) {
            ins.destroy();
        }

        this.el = el;
        this.$el = cash(el);
    }

    /**
     * Initializes components
     * @param {class} classDef
     * @param {Element | NodeList | jQuery} els
     * @param {Object} options
     */


    _createClass(Component, null, [{
        key: "init",
        value: function init(classDef, els, options) {
            var instances = null;
            if (els instanceof Element) {
                instances = new classDef(els, options);
            } else if (!!els && (els.jquery || els.cash || els instanceof NodeList)) {
                var instancesArr = [];
                for (var i = 0; i < els.length; i++) {
                    instancesArr.push(new classDef(els[i], options));
                }
                instances = instancesArr;
            }

            return instances;
        }
    }]);
    return Component;
}();
; // Required for Meteor package, the use of window prevents export by Meteor
(function (window) {
    if (window.Package) {
        M = {};
    } else {
        window.M = {};
    }

    // Check for jQuery
    M.jQueryLoaded = !!window.jQuery;
})(window);
// AMD
if (typeof define === 'function' && define.amd) {
    define('M', [], function () {
        return M;
    });
    // Common JS
} else if (typeof exports !== 'undefined' && !exports.nodeType) {
    if (typeof module !== 'undefined' && !module.nodeType && module.exports) {
        exports = module.exports = M;
    }
    exports.default = M;
}

M.initializeJqueryWrapper = function (plugin, pluginName, classRef) {
    jQuery.fn[pluginName] = function (methodOrOptions) {
        // Call plugin method if valid method name is passed in
        if (plugin.prototype[methodOrOptions]) {
            var params = Array.prototype.slice.call(arguments, 1);
            // Getter methods
            if (methodOrOptions.slice(0, 3) === 'get') {
                var instance = this.first()[0][classRef];
                return instance[methodOrOptions].apply(instance, params);
            }

            // Void methods
            return this.each(function () {
                var instance = this[classRef];
                instance[methodOrOptions].apply(instance, params);
            });
            // Initialize plugin if options or no argument is passed in
        } else if (typeof methodOrOptions === 'object' || !methodOrOptions) {
            plugin.init(this, arguments[0]);
            return this;
        }

        // Return error if an unrecognized  method name is passed in
        jQuery.error("Method " + methodOrOptions + " does not exist on jQuery." + pluginName);
    };
};
/*
 v2.2.0
 2017 Julian Garnier
 Released under the MIT license
 */
var $jscomp = { scope: {} }; $jscomp.defineProperty = "function" == typeof Object.defineProperties ? Object.defineProperty : function (e, r, p) {
    if (p.get || p.set) throw new TypeError("ES3 does not support getters and setters."); e != Array.prototype && e != Object.prototype && (e[r] = p.value);
}; $jscomp.getGlobal = function (e) {
    return "undefined" != typeof window && window === e ? e : "undefined" != typeof global && null != global ? global : e;
}; $jscomp.global = $jscomp.getGlobal(this); $jscomp.SYMBOL_PREFIX = "jscomp_symbol_";
$jscomp.initSymbol = function () {
    $jscomp.initSymbol = function () { }; $jscomp.global.Symbol || ($jscomp.global.Symbol = $jscomp.Symbol);
}; $jscomp.symbolCounter_ = 0; $jscomp.Symbol = function (e) {
    return $jscomp.SYMBOL_PREFIX + (e || "") + $jscomp.symbolCounter_++;
};
$jscomp.initSymbolIterator = function () {
    $jscomp.initSymbol(); var e = $jscomp.global.Symbol.iterator; e || (e = $jscomp.global.Symbol.iterator = $jscomp.global.Symbol("iterator")); "function" != typeof Array.prototype[e] && $jscomp.defineProperty(Array.prototype, e, {
        configurable: !0, writable: !0, value: function () {
            return $jscomp.arrayIterator(this);
        }
    }); $jscomp.initSymbolIterator = function () { };
}; $jscomp.arrayIterator = function (e) {
    var r = 0; return $jscomp.iteratorPrototype(function () {
        return r < e.length ? { done: !1, value: e[r++] } : { done: !0 };
    });
};
$jscomp.iteratorPrototype = function (e) {
    $jscomp.initSymbolIterator(); e = { next: e }; e[$jscomp.global.Symbol.iterator] = function () {
        return this;
    }; return e;
}; $jscomp.array = $jscomp.array || {}; $jscomp.iteratorFromArray = function (e, r) {
    $jscomp.initSymbolIterator(); e instanceof String && (e += ""); var p = 0,
        m = {
            next: function () {
                if (p < e.length) {
                    var u = p++; return { value: r(u, e[u]), done: !1 };
                } m.next = function () {
                    return { done: !0, value: void 0 };
                }; return m.next();
            }
        }; m[Symbol.iterator] = function () {
            return m;
        }; return m;
};
$jscomp.polyfill = function (e, r, p, m) {
    if (r) {
        p = $jscomp.global; e = e.split("."); for (m = 0; m < e.length - 1; m++) {
            var u = e[m]; u in p || (p[u] = {}); p = p[u];
        } e = e[e.length - 1]; m = p[e]; r = r(m); r != m && null != r && $jscomp.defineProperty(p, e, { configurable: !0, writable: !0, value: r });
    }
}; $jscomp.polyfill("Array.prototype.keys", function (e) {
    return e ? e : function () {
        return $jscomp.iteratorFromArray(this, function (e) {
            return e;
        });
    };
}, "es6-impl", "es3"); var $jscomp$this = this;
(function (r) {
    M.anime = r();
})(function () {
    function e(a) {
        if (!h.col(a)) try {
            return document.querySelectorAll(a);
        } catch (c) { }
    } function r(a, c) {
        for (var d = a.length, b = 2 <= arguments.length ? arguments[1] : void 0, f = [], n = 0; n < d; n++) {
            if (n in a) {
                var k = a[n]; c.call(b, k, n, a) && f.push(k);
            }
        } return f;
    } function p(a) {
        return a.reduce(function (a, d) {
            return a.concat(h.arr(d) ? p(d) : d);
        }, []);
    } function m(a) {
        if (h.arr(a)) return a;
        h.str(a) && (a = e(a) || a); return a instanceof NodeList || a instanceof HTMLCollection ? [].slice.call(a) : [a];
    } function u(a, c) {
        return a.some(function (a) {
            return a === c;
        });
    } function C(a) {
        var c = {},
            d; for (d in a) {
                c[d] = a[d];
            } return c;
    } function D(a, c) {
        var d = C(a),
            b; for (b in a) {
                d[b] = c.hasOwnProperty(b) ? c[b] : a[b];
            } return d;
    } function z(a, c) {
        var d = C(a),
            b; for (b in c) {
                d[b] = h.und(a[b]) ? c[b] : a[b];
            } return d;
    } function T(a) {
        a = a.replace(/^#?([a-f\d])([a-f\d])([a-f\d])$/i, function (a, c, d, k) {
            return c + c + d + d + k + k;
        }); var c = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(a);
        a = parseInt(c[1], 16); var d = parseInt(c[2], 16),
            c = parseInt(c[3], 16); return "rgba(" + a + "," + d + "," + c + ",1)";
    } function U(a) {
        function c(a, c, b) {
            0 > b && (b += 1); 1 < b && --b; return b < 1 / 6 ? a + 6 * (c - a) * b : .5 > b ? c : b < 2 / 3 ? a + (c - a) * (2 / 3 - b) * 6 : a;
        } var d = /hsl\((\d+),\s*([\d.]+)%,\s*([\d.]+)%\)/g.exec(a) || /hsla\((\d+),\s*([\d.]+)%,\s*([\d.]+)%,\s*([\d.]+)\)/g.exec(a); a = parseInt(d[1]) / 360; var b = parseInt(d[2]) / 100,
            f = parseInt(d[3]) / 100,
            d = d[4] || 1; if (0 == b) f = b = a = f; else {
                var n = .5 > f ? f * (1 + b) : f + b - f * b,
                    k = 2 * f - n,
                    f = c(k, n, a + 1 / 3),
                    b = c(k, n, a); a = c(k, n, a - 1 / 3);
            } return "rgba(" + 255 * f + "," + 255 * b + "," + 255 * a + "," + d + ")";
    } function y(a) {
        if (a = /([\+\-]?[0-9#\.]+)(%|px|pt|em|rem|in|cm|mm|ex|ch|pc|vw|vh|vmin|vmax|deg|rad|turn)?$/.exec(a)) return a[2];
    } function V(a) {
        if (- 1 < a.indexOf("translate") || "perspective" === a) return "px"; if (- 1 < a.indexOf("rotate") || - 1 < a.indexOf("skew")) return "deg";
    } function I(a, c) {
        return h.fnc(a) ? a(c.target, c.id, c.total) : a;
    } function E(a, c) {
        if (c in a.style) return getComputedStyle(a).getPropertyValue(c.replace(/([a-z])([A-Z])/g, "$1-$2").toLowerCase()) || "0";
    } function J(a, c) {
        if (h.dom(a) && u(W, c)) return "transform"; if (h.dom(a) && (a.getAttribute(c) || h.svg(a) && a[c])) return "attribute"; if (h.dom(a) && "transform" !== c && E(a, c)) return "css"; if (null != a[c]) return "object";
    } function X(a, c) {
        var d = V(c),
            d = - 1 < c.indexOf("scale") ? 1 : 0 + d; a = a.style.transform; if (!a) return d; for (var b = [], f = [], n = [], k = /(\w+)\((.+?)\)/g; b = k.exec(a);) {
                f.push(b[1]), n.push(b[2]);
            } a = r(n, function (a, b) {
                return f[b] === c;
            }); return a.length ? a[0] : d;
    } function K(a, c) {
        switch (J(a, c)) {
            case "transform":
                return X(a, c); case "css":
                return E(a, c); case "attribute":
                return a.getAttribute(c);
        }return a[c] || 0;
    } function L(a, c) {
        var d = /^(\*=|\+=|-=)/.exec(a); if (!d) return a; var b = y(a) || 0; c = parseFloat(c); a = parseFloat(a.replace(d[0], "")); switch (d[0][0]) {
            case "+":
                return c + a + b; case "-":
                return c - a + b; case "*":
                return c * a + b;
        }
    } function F(a, c) {
        return Math.sqrt(Math.pow(c.x - a.x, 2) + Math.pow(c.y - a.y, 2));
    } function M(a) {
        a = a.points; for (var c = 0, d, b = 0; b < a.numberOfItems; b++) {
            var f = a.getItem(b); 0 < b && (c += F(d, f)); d = f;
        } return c;
    } function N(a) {
        if (a.getTotalLength) return a.getTotalLength(); switch (a.tagName.toLowerCase()) {
            case "circle":
                return 2 * Math.PI * a.getAttribute("r"); case "rect":
                return 2 * a.getAttribute("width") + 2 * a.getAttribute("height"); case "line":
                return F({ x: a.getAttribute("x1"), y: a.getAttribute("y1") }, { x: a.getAttribute("x2"), y: a.getAttribute("y2") }); case "polyline":
                return M(a); case "polygon":
                var c = a.points; return M(a) + F(c.getItem(c.numberOfItems - 1), c.getItem(0));
        }
    } function Y(a, c) {
        function d(b) {
            b = void 0 === b ? 0 : b; return a.el.getPointAtLength(1 <= c + b ? c + b : 0);
        } var b = d(),
            f = d(- 1),
            n = d(1); switch (a.property) {
                case "x":
                    return b.x; case "y":
                    return b.y;
                case "angle":
                    return 180 * Math.atan2(n.y - f.y, n.x - f.x) / Math.PI;
            }
    } function O(a, c) {
        var d = /-?\d*\.?\d+/g,
            b; b = h.pth(a) ? a.totalLength : a; if (h.col(b)) {
                if (h.rgb(b)) {
                    var f = /rgb\((\d+,\s*[\d]+,\s*[\d]+)\)/g.exec(b); b = f ? "rgba(" + f[1] + ",1)" : b;
                } else b = h.hex(b) ? T(b) : h.hsl(b) ? U(b) : void 0;
            } else f = (f = y(b)) ? b.substr(0, b.length - f.length) : b, b = c && !/\s/g.test(b) ? f + c : f; b += ""; return { original: b, numbers: b.match(d) ? b.match(d).map(Number) : [0], strings: h.str(a) || c ? b.split(d) : [] };
    } function P(a) {
        a = a ? p(h.arr(a) ? a.map(m) : m(a)) : []; return r(a, function (a, d, b) {
            return b.indexOf(a) === d;
        });
    } function Z(a) {
        var c = P(a); return c.map(function (a, b) {
            return { target: a, id: b, total: c.length };
        });
    } function aa(a, c) {
        var d = C(c); if (h.arr(a)) {
            var b = a.length; 2 !== b || h.obj(a[0]) ? h.fnc(c.duration) || (d.duration = c.duration / b) : a = { value: a };
        } return m(a).map(function (a, b) {
            b = b ? 0 : c.delay; a = h.obj(a) && !h.pth(a) ? a : { value: a }; h.und(a.delay) && (a.delay = b); return a;
        }).map(function (a) {
            return z(a, d);
        });
    } function ba(a, c) {
        var d = {},
            b; for (b in a) {
                var f = I(a[b], c); h.arr(f) && (f = f.map(function (a) {
                    return I(a, c);
                }), 1 === f.length && (f = f[0])); d[b] = f;
            } d.duration = parseFloat(d.duration); d.delay = parseFloat(d.delay); return d;
    } function ca(a) {
        return h.arr(a) ? A.apply(this, a) : Q[a];
    } function da(a, c) {
        var d; return a.tweens.map(function (b) {
            b = ba(b, c); var f = b.value,
                e = K(c.target, a.name),
                k = d ? d.to.original : e,
                k = h.arr(f) ? f[0] : k,
                w = L(h.arr(f) ? f[1] : f, k),
                e = y(w) || y(k) || y(e); b.from = O(k, e); b.to = O(w, e); b.start = d ? d.end : a.offset; b.end = b.start + b.delay + b.duration; b.easing = ca(b.easing); b.elasticity = (1E3 - Math.min(Math.max(b.elasticity, 1), 999)) / 1E3; b.isPath = h.pth(f); b.isColor = h.col(b.from.original); b.isColor && (b.round = 1); return d = b;
        });
    } function ea(a, c) {
        return r(p(a.map(function (a) {
            return c.map(function (b) {
                var c = J(a.target, b.name); if (c) {
                    var d = da(b, a); b = { type: c, property: b.name, animatable: a, tweens: d, duration: d[d.length - 1].end, delay: d[0].delay };
                } else b = void 0; return b;
            });
        })), function (a) {
            return !h.und(a);
        });
    } function R(a, c, d, b) {
        var f = "delay" === a; return c.length ? (f ? Math.min : Math.max).apply(Math, c.map(function (b) {
            return b[a];
        })) : f ? b.delay : d.offset + b.delay + b.duration;
    } function fa(a) {
        var c = D(ga, a),
            d = D(S, a),
            b = Z(a.targets),
            f = [],
            e = z(c, d),
            k; for (k in a) {
                e.hasOwnProperty(k) || "targets" === k || f.push({ name: k, offset: e.offset, tweens: aa(a[k], d) });
            } a = ea(b, f); return z(c, { children: [], animatables: b, animations: a, duration: R("duration", a, c, d), delay: R("delay", a, c, d) });
    } function q(a) {
        function c() {
            return window.Promise && new Promise(function (a) {
                return p = a;
            });
        } function d(a) {
            return g.reversed ? g.duration - a : a;
        } function b(a) {
            for (var b = 0, c = {}, d = g.animations, f = d.length; b < f;) {
                var e = d[b],
                    k = e.animatable,
                    h = e.tweens,
                    n = h.length - 1,
                    l = h[n]; n && (l = r(h, function (b) {
                        return a < b.end;
                    })[0] || l); for (var h = Math.min(Math.max(a - l.start - l.delay, 0), l.duration) / l.duration, w = isNaN(h) ? 1 : l.easing(h, l.elasticity), h = l.to.strings, p = l.round, n = [], m = void 0, m = l.to.numbers.length, t = 0; t < m; t++) {
                        var x = void 0,
                            x = l.to.numbers[t],
                            q = l.from.numbers[t],
                            x = l.isPath ? Y(l.value, w * x) : q + w * (x - q); p && (l.isColor && 2 < t || (x = Math.round(x * p) / p)); n.push(x);
                    } if (l = h.length) for (m = h[0], w = 0; w < l; w++) {
                        p = h[w + 1], t = n[w], isNaN(t) || (m = p ? m + (t + p) : m + (t + " "));
                    } else m = n[0]; ha[e.type](k.target, e.property, m, c, k.id); e.currentValue = m; b++;
            } if (b = Object.keys(c).length) for (d = 0; d < b; d++) {
                H || (H = E(document.body, "transform") ? "transform" : "-webkit-transform"), g.animatables[d].target.style[H] = c[d].join(" ");
            } g.currentTime = a; g.progress = a / g.duration * 100;
        } function f(a) {
            if (g[a]) g[a](g);
        } function e() {
            g.remaining && !0 !== g.remaining && g.remaining--;
        } function k(a) {
            var k = g.duration,
                n = g.offset,
                w = n + g.delay,
                r = g.currentTime,
                x = g.reversed,
                q = d(a); if (g.children.length) {
                    var u = g.children,
                        v = u.length;
                    if (q >= g.currentTime) for (var G = 0; G < v; G++) {
                        u[G].seek(q);
                    } else for (; v--;) {
                        u[v].seek(q);
                    }
                } if (q >= w || !k) g.began || (g.began = !0, f("begin")), f("run"); if (q > n && q < k) b(q); else if (q <= n && 0 !== r && (b(0), x && e()), q >= k && r !== k || !k) b(k), x || e(); f("update"); a >= k && (g.remaining ? (t = h, "alternate" === g.direction && (g.reversed = !g.reversed)) : (g.pause(), g.completed || (g.completed = !0, f("complete"), "Promise" in window && (p(), m = c()))), l = 0);
        } a = void 0 === a ? {} : a; var h,
            t,
            l = 0,
            p = null,
            m = c(),
            g = fa(a); g.reset = function () {
                var a = g.direction,
                    c = g.loop; g.currentTime = 0; g.progress = 0; g.paused = !0; g.began = !1; g.completed = !1; g.reversed = "reverse" === a; g.remaining = "alternate" === a && 1 === c ? 2 : c; b(0); for (a = g.children.length; a--;) {
                        g.children[a].reset();
                    }
            }; g.tick = function (a) {
                h = a; t || (t = h); k((l + h - t) * q.speed);
            }; g.seek = function (a) {
                k(d(a));
            }; g.pause = function () {
                var a = v.indexOf(g); - 1 < a && v.splice(a, 1); g.paused = !0;
            }; g.play = function () {
                g.paused && (g.paused = !1, t = 0, l = d(g.currentTime), v.push(g), B || ia());
            }; g.reverse = function () {
                g.reversed = !g.reversed; t = 0; l = d(g.currentTime);
            }; g.restart = function () {
                g.pause();
                g.reset(); g.play();
            }; g.finished = m; g.reset(); g.autoplay && g.play(); return g;
    } var ga = { update: void 0, begin: void 0, run: void 0, complete: void 0, loop: 1, direction: "normal", autoplay: !0, offset: 0 },
        S = { duration: 1E3, delay: 0, easing: "easeOutElastic", elasticity: 500, round: 0 },
        W = "translateX translateY translateZ rotate rotateX rotateY rotateZ scale scaleX scaleY scaleZ skewX skewY perspective".split(" "),
        H,
        h = {
            arr: function (a) {
                return Array.isArray(a);
            }, obj: function (a) {
                return - 1 < Object.prototype.toString.call(a).indexOf("Object");
            },
            pth: function (a) {
                return h.obj(a) && a.hasOwnProperty("totalLength");
            }, svg: function (a) {
                return a instanceof SVGElement;
            }, dom: function (a) {
                return a.nodeType || h.svg(a);
            }, str: function (a) {
                return "string" === typeof a;
            }, fnc: function (a) {
                return "function" === typeof a;
            }, und: function (a) {
                return "undefined" === typeof a;
            }, hex: function (a) {
                return (/(^#[0-9A-F]{6}$)|(^#[0-9A-F]{3}$)/i.test(a)
                );
            }, rgb: function (a) {
                return (/^rgb/.test(a)
                );
            }, hsl: function (a) {
                return (/^hsl/.test(a)
                );
            }, col: function (a) {
                return h.hex(a) || h.rgb(a) || h.hsl(a);
            }
        },
        A = function () {
            function a(a, d, b) {
                return (((1 - 3 * b + 3 * d) * a + (3 * b - 6 * d)) * a + 3 * d) * a;
            } return function (c, d, b, f) {
                if (0 <= c && 1 >= c && 0 <= b && 1 >= b) {
                    var e = new Float32Array(11); if (c !== d || b !== f) for (var k = 0; 11 > k; ++k) {
                        e[k] = a(.1 * k, c, b);
                    } return function (k) {
                        if (c === d && b === f) return k; if (0 === k) return 0; if (1 === k) return 1; for (var h = 0, l = 1; 10 !== l && e[l] <= k; ++l) {
                            h += .1;
                        } --l; var l = h + (k - e[l]) / (e[l + 1] - e[l]) * .1,
                            n = 3 * (1 - 3 * b + 3 * c) * l * l + 2 * (3 * b - 6 * c) * l + 3 * c; if (.001 <= n) {
                                for (h = 0; 4 > h; ++h) {
                                    n = 3 * (1 - 3 * b + 3 * c) * l * l + 2 * (3 * b - 6 * c) * l + 3 * c; if (0 === n) break; var m = a(l, c, b) - k,
                                        l = l - m / n;
                                } k = l;
                            } else if (0 === n) k = l; else {
                                var l = h,
                                    h = h + .1,
                                    g = 0; do {
                                        m = l + (h - l) / 2, n = a(m, c, b) - k, 0 < n ? h = m : l = m;
                                    } while (1e-7 < Math.abs(n) && 10 > ++g); k = m;
                            } return a(k, d, f);
                    };
                }
            };
        }(),
        Q = function () {
            function a(a, b) {
                return 0 === a || 1 === a ? a : - Math.pow(2, 10 * (a - 1)) * Math.sin(2 * (a - 1 - b / (2 * Math.PI) * Math.asin(1)) * Math.PI / b);
            } var c = "Quad Cubic Quart Quint Sine Expo Circ Back Elastic".split(" "),
                d = {
                    In: [[.55, .085, .68, .53], [.55, .055, .675, .19], [.895, .03, .685, .22], [.755, .05, .855, .06], [.47, 0, .745, .715], [.95, .05, .795, .035], [.6, .04, .98, .335], [.6, - .28, .735, .045], a], Out: [[.25, .46, .45, .94], [.215, .61, .355, 1], [.165, .84, .44, 1], [.23, 1, .32, 1], [.39, .575, .565, 1], [.19, 1, .22, 1], [.075, .82, .165, 1], [.175, .885, .32, 1.275], function (b, c) {
                        return 1 - a(1 - b, c);
                    }], InOut: [[.455, .03, .515, .955], [.645, .045, .355, 1], [.77, 0, .175, 1], [.86, 0, .07, 1], [.445, .05, .55, .95], [1, 0, 0, 1], [.785, .135, .15, .86], [.68, - .55, .265, 1.55], function (b, c) {
                        return .5 > b ? a(2 * b, c) / 2 : 1 - a(- 2 * b + 2, c) / 2;
                    }]
                },
                b = { linear: A(.25, .25, .75, .75) },
                f = {},
                e; for (e in d) {
                    f.type = e, d[f.type].forEach(function (a) {
                        return function (d, f) {
                            b["ease" + a.type + c[f]] = h.fnc(d) ? d : A.apply($jscomp$this, d);
                        };
                    }(f)), f = { type: f.type };
                } return b;
        }(),
        ha = {
            css: function (a, c, d) {
                return a.style[c] = d;
            }, attribute: function (a, c, d) {
                return a.setAttribute(c, d);
            }, object: function (a, c, d) {
                return a[c] = d;
            }, transform: function (a, c, d, b, f) {
                b[f] || (b[f] = []); b[f].push(c + "(" + d + ")");
            }
        },
        v = [],
        B = 0,
        ia = function () {
            function a() {
                B = requestAnimationFrame(c);
            } function c(c) {
                var b = v.length; if (b) {
                    for (var d = 0; d < b;) {
                        v[d] && v[d].tick(c), d++;
                    } a();
                } else cancelAnimationFrame(B), B = 0;
            } return a;
        }(); q.version = "2.2.0"; q.speed = 1; q.running = v; q.remove = function (a) {
            a = P(a); for (var c = v.length; c--;) {
                for (var d = v[c], b = d.animations, f = b.length; f--;) {
                    u(a, b[f].animatable.target) && (b.splice(f, 1), b.length || d.pause());
                }
            }
        }; q.getValue = K; q.path = function (a, c) {
            var d = h.str(a) ? e(a)[0] : a,
                b = c || 100; return function (a) {
                    return { el: d, property: a, totalLength: N(d) * (b / 100) };
                };
        }; q.setDashoffset = function (a) {
            var c = N(a); a.setAttribute("stroke-dasharray", c); return c;
        }; q.bezier = A; q.easings = Q; q.timeline = function (a) {
            var c = q(a); c.pause(); c.duration = 0; c.add = function (d) {
                c.children.forEach(function (a) {
                    a.began = !0; a.completed = !0;
                }); m(d).forEach(function (b) {
                    var d = z(b, D(S, a || {})); d.targets = d.targets || a.targets; b = c.duration; var e = d.offset; d.autoplay = !1; d.direction = c.direction; d.offset = h.und(e) ? b : L(e, b); c.began = !0; c.completed = !0; c.seek(d.offset); d = q(d); d.began = !0; d.completed = !0; d.duration > b && (c.duration = d.duration); c.children.push(d);
                }); c.seek(0); c.reset(); c.autoplay && c.restart(); return c;
            }; return c;
        }; q.random = function (a, c) {
            return Math.floor(Math.random() * (c - a + 1)) + a;
        }; return q;
});
; (function ($, anim) {
    'use strict';
    var _defaults = {
        accordion: true,
        onOpenStart: undefined,
        onOpenEnd: undefined,
        onCloseStart: undefined,
        onCloseEnd: undefined,
        inDuration: 300,
        outDuration: 300
    };
    /**
     * @class
     *
     */

    var Collapsible = function (_Component) {
        _inherits(Collapsible, _Component);
        /**
         * Construct Collapsible instance
         * @constructor
         * @param {Element} el
         * @param {Object} options
         */
        function Collapsible(el, options) {
            _classCallCheck(this, Collapsible);
            var _this3 = _possibleConstructorReturn(this, (Collapsible.__proto__ || Object.getPrototypeOf(Collapsible)).call(this, Collapsible, el, options));
            _this3.el.M_Collapsible = _this3;
            /**
             * Options for the collapsible
             * @member Collapsible#options
             * @prop {Boolean} [accordion=false] - Type of the collapsible
             * @prop {Function} onOpenStart - Callback function called before collapsible is opened
             * @prop {Function} onOpenEnd - Callback function called after collapsible is opened
             * @prop {Function} onCloseStart - Callback function called before collapsible is closed
             * @prop {Function} onCloseEnd - Callback function called after collapsible is closed
             * @prop {Number} inDuration - Transition in duration in milliseconds.
             * @prop {Number} outDuration - Transition duration in milliseconds.
             */
            _this3.options = $.extend({}, Collapsible.defaults, options);
            // Setup tab indices
            _this3.$headers = _this3.$el.children('li').children('.collapsible-header');
            _this3.$headers.attr('tabindex', 0);
            _this3._setupEventHandlers();
            // Open first active
            var $activeBodies = _this3.$el.children('li.active').children('.collapsible-body');
            if (_this3.options.accordion) {
                // Handle Accordion
                $activeBodies.first().css('display', 'block');
            } else {
                // Handle Expandables
                $activeBodies.css('display', 'block');
            }
            return _this3;
        }

        _createClass(Collapsible, [{
            key: "destroy",
            /**
             * Teardown component
             */
            value: function destroy() {
                this._removeEventHandlers();
                this.el.M_Collapsible = undefined;
            }

            /**
             * Setup Event Handlers
             */

        }, {
            key: "_setupEventHandlers",
            value: function _setupEventHandlers() {
                var _this4 = this;
                this._handleCollapsibleClickBound = this._handleCollapsibleClick.bind(this);
                this._handleCollapsibleKeydownBound = this._handleCollapsibleKeydown.bind(this);
                this.el.addEventListener('click', this._handleCollapsibleClickBound);
                this.$headers.each(function (header) {
                    header.addEventListener('keydown', _this4._handleCollapsibleKeydownBound);
                });
            }

            /**
             * Remove Event Handlers
             */

        }, {
            key: "_removeEventHandlers",
            value: function _removeEventHandlers() {
                var _this5 = this;
                this.el.removeEventListener('click', this._handleCollapsibleClickBound);
                this.$headers.each(function (header) {
                    header.removeEventListener('keydown', _this5._handleCollapsibleKeydownBound);
                });
            }

            /**
             * Handle Collapsible Click
             * @param {Event} e
             */

        }, {
            key: "_handleCollapsibleClick",
            value: function _handleCollapsibleClick(e) {
                var $header = $(e.target).closest('.collapsible-header');
                if (e.target && $header.length) {
                    var $collapsible = $header.closest('.collapsible');
                    if ($collapsible[0] === this.el) {
                        var $collapsibleLi = $header.closest('li');
                        var $collapsibleLis = $collapsible.children('li');
                        var isActive = $collapsibleLi[0].classList.contains('active');
                        var index = $collapsibleLis.index($collapsibleLi);
                        if (isActive) {
                            this.close(index);
                        } else {
                            this.open(index);
                        }
                    }
                }
            }

            /**
             * Handle Collapsible Keydown
             * @param {Event} e
             */

        }, {
            key: "_handleCollapsibleKeydown",
            value: function _handleCollapsibleKeydown(e) {
                if (e.keyCode === 13) {
                    this._handleCollapsibleClickBound(e);
                }
            }

            /**
             * Animate in collapsible slide
             * @param {Number} index - 0th index of slide
             */

        }, {
            key: "_animateIn",
            value: function _animateIn(index) {
                var _this6 = this;
                var $collapsibleLi = this.$el.children('li').eq(index);
                if ($collapsibleLi.length) {
                    var $body = $collapsibleLi.children('.collapsible-body');
                    anim.remove($body[0]);
                    $body.css({
                        display: 'block',
                        overflow: 'hidden',
                        height: 0,
                        paddingTop: '',
                        paddingBottom: ''
                    });
                    var pTop = $body.css('padding-top');
                    var pBottom = $body.css('padding-bottom');
                    var finalHeight = $body[0].scrollHeight;
                    $body.css({
                        paddingTop: 0,
                        paddingBottom: 0
                    });
                    anim({
                        targets: $body[0],
                        height: finalHeight,
                        paddingTop: pTop,
                        paddingBottom: pBottom,
                        duration: this.options.inDuration,
                        easing: 'easeInOutCubic',
                        complete: function (anim) {
                            $body.css({
                                overflow: '',
                                paddingTop: '',
                                paddingBottom: '',
                                height: ''
                            });
                            // onOpenEnd callback
                            if (typeof _this6.options.onOpenEnd === 'function') {
                                _this6.options.onOpenEnd.call(_this6, $collapsibleLi[0]);
                            }
                        }
                    });
                }
            }

            /**
             * Animate out collapsible slide
             * @param {Number} index - 0th index of slide to open
             */

        }, {
            key: "_animateOut",
            value: function _animateOut(index) {
                var _this7 = this;
                var $collapsibleLi = this.$el.children('li').eq(index);
                if ($collapsibleLi.length) {
                    var $body = $collapsibleLi.children('.collapsible-body');
                    anim.remove($body[0]);
                    $body.css('overflow', 'hidden');
                    anim({
                        targets: $body[0],
                        height: 0,
                        paddingTop: 0,
                        paddingBottom: 0,
                        duration: this.options.outDuration,
                        easing: 'easeInOutCubic',
                        complete: function () {
                            $body.css({
                                height: '',
                                overflow: '',
                                padding: '',
                                display: ''
                            });
                            // onCloseEnd callback
                            if (typeof _this7.options.onCloseEnd === 'function') {
                                _this7.options.onCloseEnd.call(_this7, $collapsibleLi[0]);
                            }
                        }
                    });
                }
            }

            /**
             * Open Collapsible
             * @param {Number} index - 0th index of slide
             */

        }, {
            key: "open",
            value: function open(index) {
                var _this8 = this;
                var $collapsibleLi = this.$el.children('li').eq(index);
                if ($collapsibleLi.length && !$collapsibleLi[0].classList.contains('active')) {
                    // onOpenStart callback
                    if (typeof this.options.onOpenStart === 'function') {
                        this.options.onOpenStart.call(this, $collapsibleLi[0]);
                    }

                    // Handle accordion behavior
                    if (this.options.accordion) {
                        var $collapsibleLis = this.$el.children('li');
                        var $activeLis = this.$el.children('li.active');
                        $activeLis.each(function (el) {
                            var index = $collapsibleLis.index($(el));
                            _this8.close(index);
                        });
                    }

                    // Animate in
                    $collapsibleLi[0].classList.add('active');
                    this._animateIn(index);
                }
            }

            /**
             * Close Collapsible
             * @param {Number} index - 0th index of slide
             */

        }, {
            key: "close",
            value: function close(index) {
                var $collapsibleLi = this.$el.children('li').eq(index);
                if ($collapsibleLi.length && $collapsibleLi[0].classList.contains('active')) {
                    // onCloseStart callback
                    if (typeof this.options.onCloseStart === 'function') {
                        this.options.onCloseStart.call(this, $collapsibleLi[0]);
                    }

                    // Animate out
                    $collapsibleLi[0].classList.remove('active');
                    this._animateOut(index);
                }
            }
        }], [{
            key: "init",
            value: function init(els, options) {
                return _get(Collapsible.__proto__ || Object.getPrototypeOf(Collapsible), "init", this).call(this, this, els, options);
            }

            /**
             * Get Instance
             */

        }, {
            key: "getInstance",
            value: function getInstance(el) {
                var domElem = !!el.jquery ? el[0] : el;
                return domElem.M_Collapsible;
            }
        }, {
            key: "defaults",
            get: function () {
                return _defaults;
            }
        }]);
        return Collapsible;
    }(Component);
    M.Collapsible = Collapsible;
    if (M.jQueryLoaded) {
        M.initializeJqueryWrapper(Collapsible, 'collapsible', 'M_Collapsible');
    }
})(cash, M.anime);