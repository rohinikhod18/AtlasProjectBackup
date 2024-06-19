$(document).ready(function() {
	setAdministrationPermission();
});
var updatedBatchId = 1;
// Returns a function, that, as long as it continues to be invoked, will not
// be triggered. The function will be called after it stops being called for
// N milliseconds. If `immediate` is passed, trigger the function on the
// leading edge, instead of the trailing.
function debounce(func, wait, immediate) {
	var timeout;
	return function() {
		var context = this, args = arguments;
		var later = function() {
			timeout = null;
			if (!immediate) func.apply(context, args);
		};
		var callNow = immediate && !timeout;
		clearTimeout(timeout);
		timeout = setTimeout(later, wait);
		if (callNow) func.apply(context, args);
	};
};
(function ($) {

    $.fn.isOnScreen = function(x, y) {

        if(x == null || typeof x == 'undefined') x = 1;
        if(y == null || typeof y == 'undefined') y = 1;

        var win = $(window);

        var viewport = {
            top : win.scrollTop(),
            left : win.scrollLeft()
        };
        viewport.right = viewport.left + win.width();
        viewport.bottom = viewport.top + win.height();

        var height = this.outerHeight();
        var width = this.outerWidth();

        if(!width || !height){
            return false;
        }

        var bounds = this.offset();
        bounds.right = bounds.left + width;
        bounds.bottom = bounds.top + height;

        var visible = (!(viewport.right < bounds.left || viewport.left > bounds.right || viewport.bottom < bounds.top || viewport.top > bounds.bottom));

        if(!visible){
            return false;
        }

        var deltas = {
            top : Math.min( 1, ( bounds.bottom - viewport.top ) / height),
            bottom : Math.min(1, ( viewport.bottom - bounds.top ) / height),
            left : Math.min(1, ( bounds.right - viewport.left ) / width),
            right : Math.min(1, ( viewport.right - bounds.left ) / width)
        };

        return (deltas.left * deltas.right) >= x && (deltas.top * deltas.bottom) >= y;

    };

})(jQuery);

/*
 * ! jQuery UI - v1.12.1 - 2017-02-24 http://jqueryui.com Includes: widget.js,
 * data.js, disable-selection.js, keycode.js, scroll-parent.js,
 * widgets/draggable.js, widgets/resizable.js, widgets/sortable.js,
 * widgets/datepicker.js, widgets/mouse.js, widgets/slider.js Copyright jQuery
 * Foundation and other contributors; Licensed MIT
 */

(function( factory ) {
	if ( typeof define === "function" && define.amd ) {

		// AMD. Register as an anonymous module.
		define([ "jquery" ], factory );
	} else {

		// Browser globals
		factory( jQuery );
	}
}(function( $ ) {

$.ui = $.ui || {};

var version = $.ui.version = "1.12.1";


/*
 * ! jQuery UI Widget 1.12.1 http://jqueryui.com
 * 
 * Copyright jQuery Foundation and other contributors Released under the MIT
 * license. http://jquery.org/license
 */

// >>label: Widget
// >>group: Core
// >>description: Provides a factory for creating stateful widgets with a common
// API.
// >>docs: http://api.jqueryui.com/jQuery.widget/
// >>demos: http://jqueryui.com/widget/



var widgetUuid = 0;
var widgetSlice = Array.prototype.slice;


$.cleanData = ( function( orig ) {
	return function( elems ) {
		var events, elem, i;
		for ( i = 0; ( elem = elems[ i ] ) != null; i++ ) {
			try {

				// Only trigger remove when necessary to save time
				events = $._data( elem, "events" );
				if ( events && events.remove ) {
					$( elem ).triggerHandler( "remove" );
				}

			// Http://bugs.jquery.com/ticket/8235
			} catch ( e ) {}
		}
		orig( elems );
	};
} )( $.cleanData );

$.widget = function( name, base, prototype ) {
	var existingConstructor, constructor, basePrototype;

	// ProxiedPrototype allows the provided prototype to remain unmodified
	// so that it can be used as a mixin for multiple widgets (#8876)
	var proxiedPrototype = {};

	var namespace = name.split( "." )[ 0 ];
	name = name.split( "." )[ 1 ];
	var fullName = namespace + "-" + name;

	if ( !prototype ) {
		prototype = base;
		base = $.Widget;
	}

	if ( $.isArray( prototype ) ) {
		prototype = $.extend.apply( null, [ {} ].concat( prototype ) );
	}

	// Create selector for plugin
	$.expr[ ":" ][ fullName.toLowerCase() ] = function( elem ) {
		return !!$.data( elem, fullName );
	};

	$[ namespace ] = $[ namespace ] || {};
	existingConstructor = $[ namespace ][ name ];
	constructor = $[ namespace ][ name ] = function( options, element ) {

		// Allow instantiation without "new" keyword
		if ( !this._createWidget ) {
			return new constructor( options, element );
		}

		// Allow instantiation without initializing for simple inheritance
		// must use "new" keyword (the code above always passes args)
		if ( arguments.length ) {
			this._createWidget( options, element );
		}
	};

	// Extend with the existing constructor to carry over any static properties
	$.extend( constructor, existingConstructor, {
		version: prototype.version,

		// Copy the object used to create the prototype in case we need to
		// redefine the widget later
		_proto: $.extend( {}, prototype ),

		// Track widgets that inherit from this widget in case this widget is
		// redefined after a widget inherits from it
		_childConstructors: []
	} );

	basePrototype = new base();

	// We need to make the options hash a property directly on the new instance
	// otherwise we'll modify the options hash on the prototype that we're
	// inheriting from
	basePrototype.options = $.widget.extend( {}, basePrototype.options );
	$.each( prototype, function( prop, value ) {
		if ( !$.isFunction( value ) ) {
			proxiedPrototype[ prop ] = value;
			return;
		}
		proxiedPrototype[ prop ] = ( function() {
			function _super() {
				return base.prototype[ prop ].apply( this, arguments );
			}

			function _superApply( args ) {
				return base.prototype[ prop ].apply( this, args );
			}

			return function() {
				var __super = this._super;
				var __superApply = this._superApply;
				var returnValue;

				this._super = _super;
				this._superApply = _superApply;

				returnValue = value.apply( this, arguments );

				this._super = __super;
				this._superApply = __superApply;

				return returnValue;
			};
		} )();
	} );
	constructor.prototype = $.widget.extend( basePrototype, {

		// TODO: remove support for widgetEventPrefix
		// always use the name + a colon as the prefix, e.g., draggable:start
		// don't prefix for widgets that aren't DOM-based
		widgetEventPrefix: existingConstructor ? ( basePrototype.widgetEventPrefix || name ) : name
	}, proxiedPrototype, {
		constructor: constructor,
		namespace: namespace,
		widgetName: name,
		widgetFullName: fullName
	} );

	// If this widget is being redefined then we need to find all widgets that
	// are inheriting from it and redefine all of them so that they inherit from
	// the new version of this widget. We're essentially trying to replace one
	// level in the prototype chain.
	if ( existingConstructor ) {
		$.each( existingConstructor._childConstructors, function( i, child ) {
			var childPrototype = child.prototype;

			// Redefine the child widget using the same prototype that was
			// originally used, but inherit from the new version of the base
			$.widget( childPrototype.namespace + "." + childPrototype.widgetName, constructor,
				child._proto );
		} );

		// Remove the list of existing child constructors from the old
		// constructor
		// so the old child constructors can be garbage collected
		delete existingConstructor._childConstructors;
	} else {
		base._childConstructors.push( constructor );
	}

	$.widget.bridge( name, constructor );

	return constructor;
};

$.widget.extend = function( target ) {
	var input = widgetSlice.call( arguments, 1 );
	var inputIndex = 0;
	var inputLength = input.length;
	var key;
	var value;

	for ( ; inputIndex < inputLength; inputIndex++ ) {
		for ( key in input[ inputIndex ] ) {
			value = input[ inputIndex ][ key ];
			if ( input[ inputIndex ].hasOwnProperty( key ) && value !== undefined ) {

				// Clone objects
				if ( $.isPlainObject( value ) ) {
					target[ key ] = $.isPlainObject( target[ key ] ) ?
						$.widget.extend( {}, target[ key ], value ) :

						// Don't extend strings, arrays, etc. with objects
						$.widget.extend( {}, value );

				// Copy everything else by reference
				} else {
					target[ key ] = value;
				}
			}
		}
	}
	return target;
};

$.widget.bridge = function( name, object ) {
	var fullName = object.prototype.widgetFullName || name;
	$.fn[ name ] = function( options ) {
		var isMethodCall = typeof options === "string";
		var args = widgetSlice.call( arguments, 1 );
		var returnValue = this;

		if ( isMethodCall ) {

			// If this is an empty collection, we need to have the instance
			// method
			// return undefined instead of the jQuery instance
			if ( !this.length && options === "instance" ) {
				returnValue = undefined;
			} else {
				this.each( function() {
					var methodValue;
					var instance = $.data( this, fullName );

					if ( options === "instance" ) {
						returnValue = instance;
						return false;
					}

					if ( !instance ) {
						return $.error( "cannot call methods on " + name +
							" prior to initialization; " +
							"attempted to call method '" + options + "'" );
					}

					if ( !$.isFunction( instance[ options ] ) || options.charAt( 0 ) === "_" ) {
						return $.error( "no such method '" + options + "' for " + name +
							" widget instance" );
					}

					methodValue = instance[ options ].apply( instance, args );

					if ( methodValue !== instance && methodValue !== undefined ) {
						returnValue = methodValue && methodValue.jquery ?
							returnValue.pushStack( methodValue.get() ) :
							methodValue;
						return false;
					}
				} );
			}
		} else {

			// Allow multiple hashes to be passed on init
			if ( args.length ) {
				options = $.widget.extend.apply( null, [ options ].concat( args ) );
			}

			this.each( function() {
				var instance = $.data( this, fullName );
				if ( instance ) {
					instance.option( options || {} );
					if ( instance._init ) {
						instance._init();
					}
				} else {
					$.data( this, fullName, new object( options, this ) );
				}
			} );
		}

		return returnValue;
	};
};

$.Widget = function( /* options, element */ ) {};
$.Widget._childConstructors = [];

$.Widget.prototype = {
	widgetName: "widget",
	widgetEventPrefix: "",
	defaultElement: "<div>",

	options: {
		classes: {},
		disabled: false,

		// Callbacks
		create: null
	},

	_createWidget: function( options, element ) {
		element = $( element || this.defaultElement || this )[ 0 ];
		this.element = $( element );
		this.uuid = widgetUuid++;
		this.eventNamespace = "." + this.widgetName + this.uuid;

		this.bindings = $();
		this.hoverable = $();
		this.focusable = $();
		this.classesElementLookup = {};

		if ( element !== this ) {
			$.data( element, this.widgetFullName, this );
			this._on( true, this.element, {
				remove: function( event ) {
					if ( event.target === element ) {
						this.destroy();
					}
				}
			} );
			this.document = $( element.style ?

				// Element within the document
				element.ownerDocument :

				// Element is window or document
				element.document || element );
			this.window = $( this.document[ 0 ].defaultView || this.document[ 0 ].parentWindow );
		}

		this.options = $.widget.extend( {},
			this.options,
			this._getCreateOptions(),
			options );

		this._create();

		if ( this.options.disabled ) {
			this._setOptionDisabled( this.options.disabled );
		}

		this._trigger( "create", null, this._getCreateEventData() );
		this._init();
	},

	_getCreateOptions: function() {
		return {};
	},

	_getCreateEventData: $.noop,

	_create: $.noop,

	_init: $.noop,

	destroy: function() {
		var that = this;

		this._destroy();
		$.each( this.classesElementLookup, function( key, value ) {
			that._removeClass( value, key );
		} );

		// We can probably remove the unbind calls in 2.0
		// all event bindings should go through this._on()
		this.element
			.off( this.eventNamespace )
			.removeData( this.widgetFullName );
		this.widget()
			.off( this.eventNamespace )
			.removeAttr( "aria-disabled" );

		// Clean up events and states
		this.bindings.off( this.eventNamespace );
	},

	_destroy: $.noop,

	widget: function() {
		return this.element;
	},

	option: function( key, value ) {
		var options = key;
		var parts;
		var curOption;
		var i;

		if ( arguments.length === 0 ) {

			// Don't return a reference to the internal hash
			return $.widget.extend( {}, this.options );
		}

		if ( typeof key === "string" ) {

			// Handle nested keys, e.g., "foo.bar" => { foo: { bar: ___ } }
			options = {};
			parts = key.split( "." );
			key = parts.shift();
			if ( parts.length ) {
				curOption = options[ key ] = $.widget.extend( {}, this.options[ key ] );
				for ( i = 0; i < parts.length - 1; i++ ) {
					curOption[ parts[ i ] ] = curOption[ parts[ i ] ] || {};
					curOption = curOption[ parts[ i ] ];
				}
				key = parts.pop();
				if ( arguments.length === 1 ) {
					return curOption[ key ] === undefined ? null : curOption[ key ];
				}
				curOption[ key ] = value;
			} else {
				if ( arguments.length === 1 ) {
					return this.options[ key ] === undefined ? null : this.options[ key ];
				}
				options[ key ] = value;
			}
		}

		this._setOptions( options );

		return this;
	},

	_setOptions: function( options ) {
		var key;

		for ( key in options ) {
			this._setOption( key, options[ key ] );
		}

		return this;
	},

	_setOption: function( key, value ) {
		if ( key === "classes" ) {
			this._setOptionClasses( value );
		}

		this.options[ key ] = value;

		if ( key === "disabled" ) {
			this._setOptionDisabled( value );
		}

		return this;
	},

	_setOptionClasses: function( value ) {
		var classKey, elements, currentElements;

		for ( classKey in value ) {
			currentElements = this.classesElementLookup[ classKey ];
			if ( value[ classKey ] === this.options.classes[ classKey ] ||
					!currentElements ||
					!currentElements.length ) {
				continue;
			}

			// We are doing this to create a new jQuery object because the
			// _removeClass() call
			// on the next line is going to destroy the reference to the current
			// elements being
			// tracked. We need to save a copy of this collection so that we can
			// add the new classes
			// below.
			elements = $( currentElements.get() );
			this._removeClass( currentElements, classKey );

			// We don't use _addClass() here, because that uses
			// this.options.classes
			// for generating the string of classes. We want to use the value
			// passed in from
			// _setOption(), this is the new value of the classes option which
			// was passed to
			// _setOption(). We pass this value directly to _classes().
			elements.addClass( this._classes( {
				element: elements,
				keys: classKey,
				classes: value,
				add: true
			} ) );
		}
	},

	_setOptionDisabled: function( value ) {
		this._toggleClass( this.widget(), this.widgetFullName + "-disabled", null, !!value );

		// If the widget is becoming disabled, then nothing is interactive
		if ( value ) {
			this._removeClass( this.hoverable, null, "ui-state-hover" );
			this._removeClass( this.focusable, null, "ui-state-focus" );
		}
	},

	enable: function() {
		return this._setOptions( { disabled: false } );
	},

	disable: function() {
		return this._setOptions( { disabled: true } );
	},

	_classes: function( options ) {
		var full = [];
		var that = this;

		options = $.extend( {
			element: this.element,
			classes: this.options.classes || {}
		}, options );

		function processClassString( classes, checkOption ) {
			var current, i;
			for ( i = 0; i < classes.length; i++ ) {
				current = that.classesElementLookup[ classes[ i ] ] || $();
				if ( options.add ) {
					current = $( $.unique( current.get().concat( options.element.get() ) ) );
				} else {
					current = $( current.not( options.element ).get() );
				}
				that.classesElementLookup[ classes[ i ] ] = current;
				full.push( classes[ i ] );
				if ( checkOption && options.classes[ classes[ i ] ] ) {
					full.push( options.classes[ classes[ i ] ] );
				}
			}
		}

		this._on( options.element, {
			"remove": "_untrackClassesElement"
		} );

		if ( options.keys ) {
			processClassString( options.keys.match( /\S+/g ) || [], true );
		}
		if ( options.extra ) {
			processClassString( options.extra.match( /\S+/g ) || [] );
		}

		return full.join( " " );
	},

	_untrackClassesElement: function( event ) {
		var that = this;
		$.each( that.classesElementLookup, function( key, value ) {
			if ( $.inArray( event.target, value ) !== -1 ) {
				that.classesElementLookup[ key ] = $( value.not( event.target ).get() );
			}
		} );
	},

	_removeClass: function( element, keys, extra ) {
		return this._toggleClass( element, keys, extra, false );
	},

	_addClass: function( element, keys, extra ) {
		return this._toggleClass( element, keys, extra, true );
	},

	_toggleClass: function( element, keys, extra, add ) {
		add = ( typeof add === "boolean" ) ? add : extra;
		var shift = ( typeof element === "string" || element === null ),
			options = {
				extra: shift ? keys : extra,
				keys: shift ? element : keys,
				element: shift ? this.element : element,
				add: add
			};
		options.element.toggleClass( this._classes( options ), add );
		return this;
	},

	_on: function( suppressDisabledCheck, element, handlers ) {
		var delegateElement;
		var instance = this;

		// No suppressDisabledCheck flag, shuffle arguments
		if ( typeof suppressDisabledCheck !== "boolean" ) {
			handlers = element;
			element = suppressDisabledCheck;
			suppressDisabledCheck = false;
		}

		// No element argument, shuffle and use this.element
		if ( !handlers ) {
			handlers = element;
			element = this.element;
			delegateElement = this.widget();
		} else {
			element = delegateElement = $( element );
			this.bindings = this.bindings.add( element );
		}

		$.each( handlers, function( event, handler ) {
			function handlerProxy() {

				// Allow widgets to customize the disabled handling
				// - disabled as an array instead of boolean
				// - disabled class as method for disabling individual parts
				if ( !suppressDisabledCheck &&
						( instance.options.disabled === true ||
						$( this ).hasClass( "ui-state-disabled" ) ) ) {
					return;
				}
				return ( typeof handler === "string" ? instance[ handler ] : handler )
					.apply( instance, arguments );
			}

			// Copy the guid so direct unbinding works
			if ( typeof handler !== "string" ) {
				handlerProxy.guid = handler.guid =
					handler.guid || handlerProxy.guid || $.guid++;
			}

			var match = event.match( /^([\w:-]*)\s*(.*)$/ );
			var eventName = match[ 1 ] + instance.eventNamespace;
			var selector = match[ 2 ];

			if ( selector ) {
				delegateElement.on( eventName, selector, handlerProxy );
			} else {
				element.on( eventName, handlerProxy );
			}
		} );
	},

	_off: function( element, eventName ) {
		eventName = ( eventName || "" ).split( " " ).join( this.eventNamespace + " " ) +
			this.eventNamespace;
		element.off( eventName ).off( eventName );

		// Clear the stack to avoid memory leaks (#10056)
		this.bindings = $( this.bindings.not( element ).get() );
		this.focusable = $( this.focusable.not( element ).get() );
		this.hoverable = $( this.hoverable.not( element ).get() );
	},

	_delay: function( handler, delay ) {
		function handlerProxy() {
			return ( typeof handler === "string" ? instance[ handler ] : handler )
				.apply( instance, arguments );
		}
		var instance = this;
		return setTimeout( handlerProxy, delay || 0 );
	},

	_hoverable: function( element ) {
		this.hoverable = this.hoverable.add( element );
		this._on( element, {
			mouseenter: function( event ) {
				this._addClass( $( event.currentTarget ), null, "ui-state-hover" );
			},
			mouseleave: function( event ) {
				this._removeClass( $( event.currentTarget ), null, "ui-state-hover" );
			}
		} );
	},

	_focusable: function( element ) {
		this.focusable = this.focusable.add( element );
		this._on( element, {
			focusin: function( event ) {
				this._addClass( $( event.currentTarget ), null, "ui-state-focus" );
			},
			focusout: function( event ) {
				this._removeClass( $( event.currentTarget ), null, "ui-state-focus" );
			}
		} );
	},

	_trigger: function( type, event, data ) {
		var prop, orig;
		var callback = this.options[ type ];

		data = data || {};
		event = $.Event( event );
		event.type = ( type === this.widgetEventPrefix ?
			type :
			this.widgetEventPrefix + type ).toLowerCase();

		// The original event may come from any element
		// so we need to reset the target on the new event
		event.target = this.element[ 0 ];

		// Copy original event properties over to the new event
		orig = event.originalEvent;
		if ( orig ) {
			for ( prop in orig ) {
				if ( !( prop in event ) ) {
					event[ prop ] = orig[ prop ];
				}
			}
		}

		this.element.trigger( event, data );
		return !( $.isFunction( callback ) &&
			callback.apply( this.element[ 0 ], [ event ].concat( data ) ) === false ||
			event.isDefaultPrevented() );
	}
};

$.each( { show: "fadeIn", hide: "fadeOut" }, function( method, defaultEffect ) {
	$.Widget.prototype[ "_" + method ] = function( element, options, callback ) {
		if ( typeof options === "string" ) {
			options = { effect: options };
		}

		var hasOptions;
		var effectName = !options ?
			method :
			options === true || typeof options === "number" ?
				defaultEffect :
				options.effect || defaultEffect;

		options = options || {};
		if ( typeof options === "number" ) {
			options = { duration: options };
		}

		hasOptions = !$.isEmptyObject( options );
		options.complete = callback;

		if ( options.delay ) {
			element.delay( options.delay );
		}

		if ( hasOptions && $.effects && $.effects.effect[ effectName ] ) {
			element[ method ]( options );
		} else if ( effectName !== method && element[ effectName ] ) {
			element[ effectName ]( options.duration, options.easing, callback );
		} else {
			element.queue( function( next ) {
				$( this )[ method ]();
				if ( callback ) {
					callback.call( element[ 0 ] );
				}
				next();
			} );
		}
	};
} );

var widget = $.widget;


/*
 * ! jQuery UI :data 1.12.1 http://jqueryui.com
 * 
 * Copyright jQuery Foundation and other contributors Released under the MIT
 * license. http://jquery.org/license
 */

// >>label: :data Selector
// >>group: Core
// >>description: Selects elements which have data stored under the specified
// key.
// >>docs: http://api.jqueryui.com/data-selector/


var data = $.extend( $.expr[ ":" ], {
	data: $.expr.createPseudo ?
		$.expr.createPseudo( function( dataName ) {
			return function( elem ) {
				return !!$.data( elem, dataName );
			};
		} ) :

		// Support: jQuery <1.8
		function( elem, i, match ) {
			return !!$.data( elem, match[ 3 ] );
		}
} );

/*
 * ! jQuery UI Disable Selection 1.12.1 http://jqueryui.com
 * 
 * Copyright jQuery Foundation and other contributors Released under the MIT
 * license. http://jquery.org/license
 */

// >>label: disableSelection
// >>group: Core
// >>description: Disable selection of text content within the set of matched
// elements.
// >>docs: http://api.jqueryui.com/disableSelection/

// This file is deprecated


var disableSelection = $.fn.extend( {
	disableSelection: ( function() {
		var eventType = "onselectstart" in document.createElement( "div" ) ?
			"selectstart" :
			"mousedown";

		return function() {
			return this.on( eventType + ".ui-disableSelection", function( event ) {
				event.preventDefault();
			} );
		};
	} )(),

	enableSelection: function() {
		return this.off( ".ui-disableSelection" );
	}
} );


/*
 * ! jQuery UI Keycode 1.12.1 http://jqueryui.com
 * 
 * Copyright jQuery Foundation and other contributors Released under the MIT
 * license. http://jquery.org/license
 */

// >>label: Keycode
// >>group: Core
// >>description: Provide keycodes as keynames
// >>docs: http://api.jqueryui.com/jQuery.ui.keyCode/


var keycode = $.ui.keyCode = {
	BACKSPACE: 8,
	COMMA: 188,
	DELETE: 46,
	DOWN: 40,
	END: 35,
	ENTER: 13,
	ESCAPE: 27,
	HOME: 36,
	LEFT: 37,
	PAGE_DOWN: 34,
	PAGE_UP: 33,
	PERIOD: 190,
	RIGHT: 39,
	SPACE: 32,
	TAB: 9,
	UP: 38
};


/*
 * ! jQuery UI Scroll Parent 1.12.1 http://jqueryui.com
 * 
 * Copyright jQuery Foundation and other contributors Released under the MIT
 * license. http://jquery.org/license
 */

// >>label: scrollParent
// >>group: Core
// >>description: Get the closest ancestor element that is scrollable.
// >>docs: http://api.jqueryui.com/scrollParent/



var scrollParent = $.fn.scrollParent = function( includeHidden ) {
	var position = this.css( "position" ),
		excludeStaticParent = position === "absolute",
		overflowRegex = includeHidden ? /(auto|scroll|hidden)/ : /(auto|scroll)/,
		scrollParent = this.parents().filter( function() {
			var parent = $( this );
			if ( excludeStaticParent && parent.css( "position" ) === "static" ) {
				return false;
			}
			return overflowRegex.test( parent.css( "overflow" ) + parent.css( "overflow-y" ) +
				parent.css( "overflow-x" ) );
		} ).eq( 0 );

	return position === "fixed" || !scrollParent.length ?
		$( this[ 0 ].ownerDocument || document ) :
		scrollParent;
};




// This file is deprecated
var ie = $.ui.ie = !!/msie [\w.]+/.exec( navigator.userAgent.toLowerCase() );

/*
 * ! jQuery UI Mouse 1.12.1 http://jqueryui.com
 * 
 * Copyright jQuery Foundation and other contributors Released under the MIT
 * license. http://jquery.org/license
 */

// >>label: Mouse
// >>group: Widgets
// >>description: Abstracts mouse-based interactions to assist in creating
// certain widgets.
// >>docs: http://api.jqueryui.com/mouse/



var mouseHandled = false;
$( document ).on( "mouseup", function() {
	mouseHandled = false;
} );

var widgetsMouse = $.widget( "ui.mouse", {
	version: "1.12.1",
	options: {
		cancel: "input, textarea, button, select, option",
		distance: 1,
		delay: 0
	},
	_mouseInit: function() {
		var that = this;

		this.element
			.on( "mousedown." + this.widgetName, function( event ) {
				return that._mouseDown( event );
			} )
			.on( "click." + this.widgetName, function( event ) {
				if ( true === $.data( event.target, that.widgetName + ".preventClickEvent" ) ) {
					$.removeData( event.target, that.widgetName + ".preventClickEvent" );
					event.stopImmediatePropagation();
					return false;
				}
			} );

		this.started = false;
	},

	// TODO: make sure destroying one instance of mouse doesn't mess with
	// other instances of mouse
	_mouseDestroy: function() {
		this.element.off( "." + this.widgetName );
		if ( this._mouseMoveDelegate ) {
			this.document
				.off( "mousemove." + this.widgetName, this._mouseMoveDelegate )
				.off( "mouseup." + this.widgetName, this._mouseUpDelegate );
		}
	},

	_mouseDown: function( event ) {

		// don't let more than one widget handle mouseStart
		if ( mouseHandled ) {
			return;
		}

		this._mouseMoved = false;

		// We may have missed mouseup (out of window)
		( this._mouseStarted && this._mouseUp( event ) );

		this._mouseDownEvent = event;

		var that = this,
			btnIsLeft = ( event.which === 1 ),

			// event.target.nodeName works around a bug in IE 8 with
			// disabled inputs (#7620)
			elIsCancel = ( typeof this.options.cancel === "string" && event.target.nodeName ?
				$( event.target ).closest( this.options.cancel ).length : false );
		if ( !btnIsLeft || elIsCancel || !this._mouseCapture( event ) ) {
			return true;
		}

		this.mouseDelayMet = !this.options.delay;
		if ( !this.mouseDelayMet ) {
			this._mouseDelayTimer = setTimeout( function() {
				that.mouseDelayMet = true;
			}, this.options.delay );
		}

		if ( this._mouseDistanceMet( event ) && this._mouseDelayMet( event ) ) {
			this._mouseStarted = ( this._mouseStart( event ) !== false );
			if ( !this._mouseStarted ) {
				event.preventDefault();
				return true;
			}
		}

		// Click event may never have fired (Gecko & Opera)
		if ( true === $.data( event.target, this.widgetName + ".preventClickEvent" ) ) {
			$.removeData( event.target, this.widgetName + ".preventClickEvent" );
		}

		// These delegates are required to keep context
		this._mouseMoveDelegate = function( event ) {
			return that._mouseMove( event );
		};
		this._mouseUpDelegate = function( event ) {
			return that._mouseUp( event );
		};

		this.document
			.on( "mousemove." + this.widgetName, this._mouseMoveDelegate )
			.on( "mouseup." + this.widgetName, this._mouseUpDelegate );

		event.preventDefault();

		mouseHandled = true;
		return true;
	},

	_mouseMove: function( event ) {

		// Only check for mouseups outside the document if you've moved inside
		// the document
		// at least once. This prevents the firing of mouseup in the case of
		// IE<9, which will
		// fire a mousemove event if content is placed under the cursor. See
		// #7778
		// Support: IE <9
		if ( this._mouseMoved ) {

			// IE mouseup check - mouseup happened when mouse was out of window
			if ( $.ui.ie && ( !document.documentMode || document.documentMode < 9 ) &&
					!event.button ) {
				return this._mouseUp( event );

			// Iframe mouseup check - mouseup occurred in another document
			} else if ( !event.which ) {

				// Support: Safari <=8 - 9
				// Safari sets which to 0 if you press any of the following keys
				// during a drag (#14461)
				if ( event.originalEvent.altKey || event.originalEvent.ctrlKey ||
						event.originalEvent.metaKey || event.originalEvent.shiftKey ) {
					this.ignoreMissingWhich = true;
				} else if ( !this.ignoreMissingWhich ) {
					return this._mouseUp( event );
				}
			}
		}

		if ( event.which || event.button ) {
			this._mouseMoved = true;
		}

		if ( this._mouseStarted ) {
			this._mouseDrag( event );
			return event.preventDefault();
		}

		if ( this._mouseDistanceMet( event ) && this._mouseDelayMet( event ) ) {
			this._mouseStarted =
				( this._mouseStart( this._mouseDownEvent, event ) !== false );
			( this._mouseStarted ? this._mouseDrag( event ) : this._mouseUp( event ) );
		}

		return !this._mouseStarted;
	},

	_mouseUp: function( event ) {
		this.document
			.off( "mousemove." + this.widgetName, this._mouseMoveDelegate )
			.off( "mouseup." + this.widgetName, this._mouseUpDelegate );

		if ( this._mouseStarted ) {
			this._mouseStarted = false;

			if ( event.target === this._mouseDownEvent.target ) {
				$.data( event.target, this.widgetName + ".preventClickEvent", true );
			}

			this._mouseStop( event );
		}

		if ( this._mouseDelayTimer ) {
			clearTimeout( this._mouseDelayTimer );
			delete this._mouseDelayTimer;
		}

		this.ignoreMissingWhich = false;
		mouseHandled = false;
		event.preventDefault();
	},

	_mouseDistanceMet: function( event ) {
		return ( Math.max(
				Math.abs( this._mouseDownEvent.pageX - event.pageX ),
				Math.abs( this._mouseDownEvent.pageY - event.pageY )
			) >= this.options.distance
		);
	},

	_mouseDelayMet: function( /* event */ ) {
		return this.mouseDelayMet;
	},

	// These are placeholder methods, to be overriden by extending plugin
	_mouseStart: function( /* event */ ) {},
	_mouseDrag: function( /* event */ ) {},
	_mouseStop: function( /* event */ ) {},
	_mouseCapture: function( /* event */ ) { return true; }
} );




// $.ui.plugin is deprecated. Use $.widget() extensions instead.
var plugin = $.ui.plugin = {
	add: function( module, option, set ) {
		var i,
			proto = $.ui[ module ].prototype;
		for ( i in set ) {
			proto.plugins[ i ] = proto.plugins[ i ] || [];
			proto.plugins[ i ].push( [ option, set[ i ] ] );
		}
	},
	call: function( instance, name, args, allowDisconnected ) {
		var i,
			set = instance.plugins[ name ];

		if ( !set ) {
			return;
		}

		if ( !allowDisconnected && ( !instance.element[ 0 ].parentNode ||
				instance.element[ 0 ].parentNode.nodeType === 11 ) ) {
			return;
		}

		for ( i = 0; i < set.length; i++ ) {
			if ( instance.options[ set[ i ][ 0 ] ] ) {
				set[ i ][ 1 ].apply( instance.element, args );
			}
		}
	}
};



var safeActiveElement = $.ui.safeActiveElement = function( document ) {
	var activeElement;

	// Support: IE 9 only
	// IE9 throws an "Unspecified error" accessing document.activeElement from
	// an <iframe>
	try {
		activeElement = document.activeElement;
	} catch ( error ) {
		activeElement = document.body;
	}

	// Support: IE 9 - 11 only
	// IE may return null instead of an element
	// Interestingly, this only seems to occur when NOT in an iframe
	if ( !activeElement ) {
		activeElement = document.body;
	}

	// Support: IE 11 only
	// IE11 returns a seemingly empty object in some cases when accessing
	// document.activeElement from an <iframe>
	if ( !activeElement.nodeName ) {
		activeElement = document.body;
	}

	return activeElement;
};



var safeBlur = $.ui.safeBlur = function( element ) {

	// Support: IE9 - 10 only
	// If the <body> is blurred, IE will switch windows, see #9420
	if ( element && element.nodeName.toLowerCase() !== "body" ) {
		$( element ).trigger( "blur" );
	}
};


/*
 * ! jQuery UI Draggable 1.12.1 http://jqueryui.com
 * 
 * Copyright jQuery Foundation and other contributors Released under the MIT
 * license. http://jquery.org/license
 */

// >>label: Draggable
// >>group: Interactions
// >>description: Enables dragging functionality for any element.
// >>docs: http://api.jqueryui.com/draggable/
// >>demos: http://jqueryui.com/draggable/
// >>css.structure: ../../themes/base/draggable.css



$.widget( "ui.draggable", $.ui.mouse, {
	version: "1.12.1",
	widgetEventPrefix: "drag",
	options: {
		addClasses: true,
		appendTo: "parent",
		axis: false,
		connectToSortable: false,
		containment: false,
		cursor: "auto",
		cursorAt: false,
		grid: false,
		handle: false,
		helper: "original",
		iframeFix: false,
		opacity: false,
		refreshPositions: false,
		revert: false,
		revertDuration: 500,
		scope: "default",
		scroll: true,
		scrollSensitivity: 20,
		scrollSpeed: 20,
		snap: false,
		snapMode: "both",
		snapTolerance: 20,
		stack: false,
		zIndex: false,

		// Callbacks
		drag: null,
		start: null,
		stop: null
	},
	_create: function() {

		if ( this.options.helper === "original" ) {
			this._setPositionRelative();
		}
		if ( this.options.addClasses ) {
			this._addClass( "ui-draggable" );
		}
		this._setHandleClassName();

		this._mouseInit();
	},

	_setOption: function( key, value ) {
		this._super( key, value );
		if ( key === "handle" ) {
			this._removeHandleClassName();
			this._setHandleClassName();
		}
	},

	_destroy: function() {
		if ( ( this.helper || this.element ).is( ".ui-draggable-dragging" ) ) {
			this.destroyOnClear = true;
			return;
		}
		this._removeHandleClassName();
		this._mouseDestroy();
	},

	_mouseCapture: function( event ) {
		var o = this.options;

		// Among others, prevent a drag on a resizable-handle
		if ( this.helper || o.disabled ||
				$( event.target ).closest( ".ui-resizable-handle" ).length > 0 ) {
			return false;
		}

		// Quit if we're not on a valid handle
		this.handle = this._getHandle( event );
		if ( !this.handle ) {
			return false;
		}

		this._blurActiveElement( event );

		this._blockFrames( o.iframeFix === true ? "iframe" : o.iframeFix );

		return true;

	},

	_blockFrames: function( selector ) {
		this.iframeBlocks = this.document.find( selector ).map( function() {
			var iframe = $( this );

			return $( "<div>" )
				.css( "position", "absolute" )
				.appendTo( iframe.parent() )
				.outerWidth( iframe.outerWidth() )
				.outerHeight( iframe.outerHeight() )
				.offset( iframe.offset() )[ 0 ];
		} );
	},

	_unblockFrames: function() {
		if ( this.iframeBlocks ) {
			this.iframeBlocks.remove();
			delete this.iframeBlocks;
		}
	},

	_blurActiveElement: function( event ) {
		var activeElement = $.ui.safeActiveElement( this.document[ 0 ] ),
			target = $( event.target );

		// Don't blur if the event occurred on an element that is within
		// the currently focused element
		// See #10527, #12472
		if ( target.closest( activeElement ).length ) {
			return;
		}

		// Blur any element that currently has focus, see #4261
		$.ui.safeBlur( activeElement );
	},

	_mouseStart: function( event ) {

		var o = this.options;

		// Create and append the visible helper
		this.helper = this._createHelper( event );

		this._addClass( this.helper, "ui-draggable-dragging" );

		// Cache the helper size
		this._cacheHelperProportions();

		// If ddmanager is used for droppables, set the global draggable
		if ( $.ui.ddmanager ) {
			$.ui.ddmanager.current = this;
		}

		/*
		 * - Position generation - This block generates everything position
		 * related - it's the core of draggables.
		 */

		// Cache the margins of the original element
		this._cacheMargins();

		// Store the helper's css position
		this.cssPosition = this.helper.css( "position" );
		this.scrollParent = this.helper.scrollParent( true );
		this.offsetParent = this.helper.offsetParent();
		this.hasFixedAncestor = this.helper.parents().filter( function() {
				return $( this ).css( "position" ) === "fixed";
			} ).length > 0;

		// The element's absolute position on the page minus margins
		this.positionAbs = this.element.offset();
		this._refreshOffsets( event );

		// Generate the original position
		this.originalPosition = this.position = this._generatePosition( event, false );
		this.originalPageX = event.pageX;
		this.originalPageY = event.pageY;

		// Adjust the mouse offset relative to the helper if "cursorAt" is
		// supplied
		( o.cursorAt && this._adjustOffsetFromHelper( o.cursorAt ) );

		// Set a containment if given in the options
		this._setContainment();

		// Trigger event + callbacks
		if ( this._trigger( "start", event ) === false ) {
			this._clear();
			return false;
		}

		// Recache the helper size
		this._cacheHelperProportions();

		// Prepare the droppable offsets
		if ( $.ui.ddmanager && !o.dropBehaviour ) {
			$.ui.ddmanager.prepareOffsets( this, event );
		}

		// Execute the drag once - this causes the helper not to be visible
		// before getting its
		// correct position
		this._mouseDrag( event, true );

		// If the ddmanager is used for droppables, inform the manager that
		// dragging has started
		// (see #5003)
		if ( $.ui.ddmanager ) {
			$.ui.ddmanager.dragStart( this, event );
		}

		return true;
	},

	_refreshOffsets: function( event ) {
		this.offset = {
			top: this.positionAbs.top - this.margins.top,
			left: this.positionAbs.left - this.margins.left,
			scroll: false,
			parent: this._getParentOffset(),
			relative: this._getRelativeOffset()
		};

		this.offset.click = {
			left: event.pageX - this.offset.left,
			top: event.pageY - this.offset.top
		};
	},

	_mouseDrag: function( event, noPropagation ) {

		// reset any necessary cached properties (see #5009)
		if ( this.hasFixedAncestor ) {
			this.offset.parent = this._getParentOffset();
		}

		// Compute the helpers position
		this.position = this._generatePosition( event, true );
		this.positionAbs = this._convertPositionTo( "absolute" );

		// Call plugins and callbacks and use the resulting position if
		// something is returned
		if ( !noPropagation ) {
			var ui = this._uiHash();
			if ( this._trigger( "drag", event, ui ) === false ) {
				this._mouseUp( new $.Event( "mouseup", event ) );
				return false;
			}
			this.position = ui.position;
		}

		this.helper[ 0 ].style.left = this.position.left + "px";
		this.helper[ 0 ].style.top = this.position.top + "px";

		if ( $.ui.ddmanager ) {
			$.ui.ddmanager.drag( this, event );
		}

		return false;
	},

	_mouseStop: function( event ) {

		// If we are using droppables, inform the manager about the drop
		var that = this,
			dropped = false;
		if ( $.ui.ddmanager && !this.options.dropBehaviour ) {
			dropped = $.ui.ddmanager.drop( this, event );
		}

		// if a drop comes from outside (a sortable)
		if ( this.dropped ) {
			dropped = this.dropped;
			this.dropped = false;
		}

		if ( ( this.options.revert === "invalid" && !dropped ) ||
				( this.options.revert === "valid" && dropped ) ||
				this.options.revert === true || ( $.isFunction( this.options.revert ) &&
				this.options.revert.call( this.element, dropped ) )
		) {
			$( this.helper ).animate(
				this.originalPosition,
				parseInt( this.options.revertDuration, 10 ),
				function() {
					if ( that._trigger( "stop", event ) !== false ) {
						that._clear();
					}
				}
			);
		} else {
			if ( this._trigger( "stop", event ) !== false ) {
				this._clear();
			}
		}

		return false;
	},

	_mouseUp: function( event ) {
		this._unblockFrames();

		// If the ddmanager is used for droppables, inform the manager that
		// dragging has stopped
		// (see #5003)
		if ( $.ui.ddmanager ) {
			$.ui.ddmanager.dragStop( this, event );
		}

		// Only need to focus if the event occurred on the draggable itself, see
		// #10527
		if ( this.handleElement.is( event.target ) ) {

			// The interaction is over; whether or not the click resulted in a
			// drag,
			// focus the element
			this.element.trigger( "focus" );
		}

		return $.ui.mouse.prototype._mouseUp.call( this, event );
	},

	cancel: function() {

		if ( this.helper.is( ".ui-draggable-dragging" ) ) {
			this._mouseUp( new $.Event( "mouseup", { target: this.element[ 0 ] } ) );
		} else {
			this._clear();
		}

		return this;

	},

	_getHandle: function( event ) {
		return this.options.handle ?
			!!$( event.target ).closest( this.element.find( this.options.handle ) ).length :
			true;
	},

	_setHandleClassName: function() {
		this.handleElement = this.options.handle ?
			this.element.find( this.options.handle ) : this.element;
		this._addClass( this.handleElement, "ui-draggable-handle" );
	},

	_removeHandleClassName: function() {
		this._removeClass( this.handleElement, "ui-draggable-handle" );
	},

	_createHelper: function( event ) {

		var o = this.options,
			helperIsFunction = $.isFunction( o.helper ),
			helper = helperIsFunction ?
				$( o.helper.apply( this.element[ 0 ], [ event ] ) ) :
				( o.helper === "clone" ?
					this.element.clone().removeAttr( "id" ) :
					this.element );

		if ( !helper.parents( "body" ).length ) {
			helper.appendTo( ( o.appendTo === "parent" ?
				this.element[ 0 ].parentNode :
				o.appendTo ) );
		}

		// Http://bugs.jqueryui.com/ticket/9446
		// a helper function can return the original element
		// which wouldn't have been set to relative in _create
		if ( helperIsFunction && helper[ 0 ] === this.element[ 0 ] ) {
			this._setPositionRelative();
		}

		if ( helper[ 0 ] !== this.element[ 0 ] &&
				!( /(fixed|absolute)/ ).test( helper.css( "position" ) ) ) {
			helper.css( "position", "absolute" );
		}

		return helper;

	},

	_setPositionRelative: function() {
		if ( !( /^(?:r|a|f)/ ).test( this.element.css( "position" ) ) ) {
			this.element[ 0 ].style.position = "relative";
		}
	},

	_adjustOffsetFromHelper: function( obj ) {
		if ( typeof obj === "string" ) {
			obj = obj.split( " " );
		}
		if ( $.isArray( obj ) ) {
			obj = { left: +obj[ 0 ], top: +obj[ 1 ] || 0 };
		}
		if ( "left" in obj ) {
			this.offset.click.left = obj.left + this.margins.left;
		}
		if ( "right" in obj ) {
			this.offset.click.left = this.helperProportions.width - obj.right + this.margins.left;
		}
		if ( "top" in obj ) {
			this.offset.click.top = obj.top + this.margins.top;
		}
		if ( "bottom" in obj ) {
			this.offset.click.top = this.helperProportions.height - obj.bottom + this.margins.top;
		}
	},

	_isRootNode: function( element ) {
		return ( /(html|body)/i ).test( element.tagName ) || element === this.document[ 0 ];
	},

	_getParentOffset: function() {

		// Get the offsetParent and cache its position
		var po = this.offsetParent.offset(),
			document = this.document[ 0 ];

		// This is a special case where we need to modify a offset calculated on
		// start, since the
		// following happened:
		// 1. The position of the helper is absolute, so it's position is
		// calculated based on the
		// next positioned parent
		// 2. The actual offset parent is a child of the scroll parent, and the
		// scroll parent isn't
		// the document, which means that the scroll is included in the initial
		// calculation of the
		// offset of the parent, and never recalculated upon drag
		if ( this.cssPosition === "absolute" && this.scrollParent[ 0 ] !== document &&
				$.contains( this.scrollParent[ 0 ], this.offsetParent[ 0 ] ) ) {
			po.left += this.scrollParent.scrollLeft();
			po.top += this.scrollParent.scrollTop();
		}

		if ( this._isRootNode( this.offsetParent[ 0 ] ) ) {
			po = { top: 0, left: 0 };
		}

		return {
			top: po.top + ( parseInt( this.offsetParent.css( "borderTopWidth" ), 10 ) || 0 ),
			left: po.left + ( parseInt( this.offsetParent.css( "borderLeftWidth" ), 10 ) || 0 )
		};

	},

	_getRelativeOffset: function() {
		if ( this.cssPosition !== "relative" ) {
			return { top: 0, left: 0 };
		}

		var p = this.element.position(),
			scrollIsRootNode = this._isRootNode( this.scrollParent[ 0 ] );

		return {
			top: p.top - ( parseInt( this.helper.css( "top" ), 10 ) || 0 ) +
				( !scrollIsRootNode ? this.scrollParent.scrollTop() : 0 ),
			left: p.left - ( parseInt( this.helper.css( "left" ), 10 ) || 0 ) +
				( !scrollIsRootNode ? this.scrollParent.scrollLeft() : 0 )
		};

	},

	_cacheMargins: function() {
		this.margins = {
			left: ( parseInt( this.element.css( "marginLeft" ), 10 ) || 0 ),
			top: ( parseInt( this.element.css( "marginTop" ), 10 ) || 0 ),
			right: ( parseInt( this.element.css( "marginRight" ), 10 ) || 0 ),
			bottom: ( parseInt( this.element.css( "marginBottom" ), 10 ) || 0 )
		};
	},

	_cacheHelperProportions: function() {
		this.helperProportions = {
			width: this.helper.outerWidth(),
			height: this.helper.outerHeight()
		};
	},

	_setContainment: function() {

		var isUserScrollable, c, ce,
			o = this.options,
			document = this.document[ 0 ];

		this.relativeContainer = null;

		if ( !o.containment ) {
			this.containment = null;
			return;
		}

		if ( o.containment === "window" ) {
			this.containment = [
				$( window ).scrollLeft() - this.offset.relative.left - this.offset.parent.left,
				$( window ).scrollTop() - this.offset.relative.top - this.offset.parent.top,
				$( window ).scrollLeft() + $( window ).width() -
					this.helperProportions.width - this.margins.left,
				$( window ).scrollTop() +
					( $( window ).height() || document.body.parentNode.scrollHeight ) -
					this.helperProportions.height - this.margins.top
			];
			return;
		}

		if ( o.containment === "document" ) {
			this.containment = [
				0,
				0,
				$( document ).width() - this.helperProportions.width - this.margins.left,
				( $( document ).height() || document.body.parentNode.scrollHeight ) -
					this.helperProportions.height - this.margins.top
			];
			return;
		}

		if ( o.containment.constructor === Array ) {
			this.containment = o.containment;
			return;
		}

		if ( o.containment === "parent" ) {
			o.containment = this.helper[ 0 ].parentNode;
		}

		c = $( o.containment );
		ce = c[ 0 ];

		if ( !ce ) {
			return;
		}

		isUserScrollable = /(scroll|auto)/.test( c.css( "overflow" ) );

		this.containment = [
			( parseInt( c.css( "borderLeftWidth" ), 10 ) || 0 ) +
				( parseInt( c.css( "paddingLeft" ), 10 ) || 0 ),
			( parseInt( c.css( "borderTopWidth" ), 10 ) || 0 ) +
				( parseInt( c.css( "paddingTop" ), 10 ) || 0 ),
			( isUserScrollable ? Math.max( ce.scrollWidth, ce.offsetWidth ) : ce.offsetWidth ) -
				( parseInt( c.css( "borderRightWidth" ), 10 ) || 0 ) -
				( parseInt( c.css( "paddingRight" ), 10 ) || 0 ) -
				this.helperProportions.width -
				this.margins.left -
				this.margins.right,
			( isUserScrollable ? Math.max( ce.scrollHeight, ce.offsetHeight ) : ce.offsetHeight ) -
				( parseInt( c.css( "borderBottomWidth" ), 10 ) || 0 ) -
				( parseInt( c.css( "paddingBottom" ), 10 ) || 0 ) -
				this.helperProportions.height -
				this.margins.top -
				this.margins.bottom
		];
		this.relativeContainer = c;
	},

	_convertPositionTo: function( d, pos ) {

		if ( !pos ) {
			pos = this.position;
		}

		var mod = d === "absolute" ? 1 : -1,
			scrollIsRootNode = this._isRootNode( this.scrollParent[ 0 ] );

		return {
			top: (

				// The absolute mouse position
				pos.top	+

				// Only for relative positioned nodes: Relative offset from
				// element to offset parent
				this.offset.relative.top * mod +

				// The offsetParent's offset without borders (offset + border)
				this.offset.parent.top * mod -
				( ( this.cssPosition === "fixed" ?
					-this.offset.scroll.top :
					( scrollIsRootNode ? 0 : this.offset.scroll.top ) ) * mod )
			),
			left: (

				// The absolute mouse position
				pos.left +

				// Only for relative positioned nodes: Relative offset from
				// element to offset parent
				this.offset.relative.left * mod +

				// The offsetParent's offset without borders (offset + border)
				this.offset.parent.left * mod	-
				( ( this.cssPosition === "fixed" ?
					-this.offset.scroll.left :
					( scrollIsRootNode ? 0 : this.offset.scroll.left ) ) * mod )
			)
		};

	},

	_generatePosition: function( event, constrainPosition ) {

		var containment, co, top, left,
			o = this.options,
			scrollIsRootNode = this._isRootNode( this.scrollParent[ 0 ] ),
			pageX = event.pageX,
			pageY = event.pageY;

		// Cache the scroll
		if ( !scrollIsRootNode || !this.offset.scroll ) {
			this.offset.scroll = {
				top: this.scrollParent.scrollTop(),
				left: this.scrollParent.scrollLeft()
			};
		}

		/*
		 * - Position constraining - Constrain the position to a mix of grid,
		 * containment.
		 */

		// If we are not dragging yet, we won't check for options
		if ( constrainPosition ) {
			if ( this.containment ) {
				if ( this.relativeContainer ) {
					co = this.relativeContainer.offset();
					containment = [
						this.containment[ 0 ] + co.left,
						this.containment[ 1 ] + co.top,
						this.containment[ 2 ] + co.left,
						this.containment[ 3 ] + co.top
					];
				} else {
					containment = this.containment;
				}

				if ( event.pageX - this.offset.click.left < containment[ 0 ] ) {
					pageX = containment[ 0 ] + this.offset.click.left;
				}
				if ( event.pageY - this.offset.click.top < containment[ 1 ] ) {
					pageY = containment[ 1 ] + this.offset.click.top;
				}
				if ( event.pageX - this.offset.click.left > containment[ 2 ] ) {
					pageX = containment[ 2 ] + this.offset.click.left;
				}
				if ( event.pageY - this.offset.click.top > containment[ 3 ] ) {
					pageY = containment[ 3 ] + this.offset.click.top;
				}
			}

			if ( o.grid ) {

				// Check for grid elements set to 0 to prevent divide by 0 error
				// causing invalid
				// argument errors in IE (see ticket #6950)
				top = o.grid[ 1 ] ? this.originalPageY + Math.round( ( pageY -
					this.originalPageY ) / o.grid[ 1 ] ) * o.grid[ 1 ] : this.originalPageY;
				pageY = containment ? ( ( top - this.offset.click.top >= containment[ 1 ] ||
					top - this.offset.click.top > containment[ 3 ] ) ?
						top :
						( ( top - this.offset.click.top >= containment[ 1 ] ) ?
							top - o.grid[ 1 ] : top + o.grid[ 1 ] ) ) : top;

				left = o.grid[ 0 ] ? this.originalPageX +
					Math.round( ( pageX - this.originalPageX ) / o.grid[ 0 ] ) * o.grid[ 0 ] :
					this.originalPageX;
				pageX = containment ? ( ( left - this.offset.click.left >= containment[ 0 ] ||
					left - this.offset.click.left > containment[ 2 ] ) ?
						left :
						( ( left - this.offset.click.left >= containment[ 0 ] ) ?
							left - o.grid[ 0 ] : left + o.grid[ 0 ] ) ) : left;
			}

			if ( o.axis === "y" ) {
				pageX = this.originalPageX;
			}

			if ( o.axis === "x" ) {
				pageY = this.originalPageY;
			}
		}

		return {
			top: (

				// The absolute mouse position
				pageY -

				// Click offset (relative to the element)
				this.offset.click.top -

				// Only for relative positioned nodes: Relative offset from
				// element to offset parent
				this.offset.relative.top -

				// The offsetParent's offset without borders (offset + border)
				this.offset.parent.top +
				( this.cssPosition === "fixed" ?
					-this.offset.scroll.top :
					( scrollIsRootNode ? 0 : this.offset.scroll.top ) )
			),
			left: (

				// The absolute mouse position
				pageX -

				// Click offset (relative to the element)
				this.offset.click.left -

				// Only for relative positioned nodes: Relative offset from
				// element to offset parent
				this.offset.relative.left -

				// The offsetParent's offset without borders (offset + border)
				this.offset.parent.left +
				( this.cssPosition === "fixed" ?
					-this.offset.scroll.left :
					( scrollIsRootNode ? 0 : this.offset.scroll.left ) )
			)
		};

	},

	_clear: function() {
		this._removeClass( this.helper, "ui-draggable-dragging" );
		if ( this.helper[ 0 ] !== this.element[ 0 ] && !this.cancelHelperRemoval ) {
			this.helper.remove();
		}
		this.helper = null;
		this.cancelHelperRemoval = false;
		if ( this.destroyOnClear ) {
			this.destroy();
		}
	},

	// From now on bulk stuff - mainly helpers

	_trigger: function( type, event, ui ) {
		ui = ui || this._uiHash();
		$.ui.plugin.call( this, type, [ event, ui, this ], true );

		// Absolute position and offset (see #6884 ) have to be recalculated
		// after plugins
		if ( /^(drag|start|stop)/.test( type ) ) {
			this.positionAbs = this._convertPositionTo( "absolute" );
			ui.offset = this.positionAbs;
		}
		return $.Widget.prototype._trigger.call( this, type, event, ui );
	},

	plugins: {},

	_uiHash: function() {
		return {
			helper: this.helper,
			position: this.position,
			originalPosition: this.originalPosition,
			offset: this.positionAbs
		};
	}

} );

$.ui.plugin.add( "draggable", "connectToSortable", {
	start: function( event, ui, draggable ) {
		var uiSortable = $.extend( {}, ui, {
			item: draggable.element
		} );

		draggable.sortables = [];
		$( draggable.options.connectToSortable ).each( function() {
			var sortable = $( this ).sortable( "instance" );

			if ( sortable && !sortable.options.disabled ) {
				draggable.sortables.push( sortable );

				// RefreshPositions is called at drag start to refresh the
				// containerCache
				// which is used in drag. This ensures it's initialized and
				// synchronized
				// with any changes that might have happened on the page since
				// initialization.
				sortable.refreshPositions();
				sortable._trigger( "activate", event, uiSortable );
			}
		} );
	},
	stop: function( event, ui, draggable ) {
		var uiSortable = $.extend( {}, ui, {
			item: draggable.element
		} );

		draggable.cancelHelperRemoval = false;

		$.each( draggable.sortables, function() {
			var sortable = this;

			if ( sortable.isOver ) {
				sortable.isOver = 0;

				// Allow this sortable to handle removing the helper
				draggable.cancelHelperRemoval = true;
				sortable.cancelHelperRemoval = false;

				// Use _storedCSS To restore properties in the sortable,
				// as this also handles revert (#9675) since the draggable
				// may have modified them in unexpected ways (#8809)
				sortable._storedCSS = {
					position: sortable.placeholder.css( "position" ),
					top: sortable.placeholder.css( "top" ),
					left: sortable.placeholder.css( "left" )
				};

				sortable._mouseStop( event );

				// Once drag has ended, the sortable should return to using
				// its original helper, not the shared helper from draggable
				sortable.options.helper = sortable.options._helper;
			} else {

				// Prevent this Sortable from removing the helper.
				// However, don't set the draggable to remove the helper
				// either as another connected Sortable may yet handle the
				// removal.
				sortable.cancelHelperRemoval = true;

				sortable._trigger( "deactivate", event, uiSortable );
			}
		} );
	},
	drag: function( event, ui, draggable ) {
		$.each( draggable.sortables, function() {
			var innermostIntersecting = false,
				sortable = this;

			// Copy over variables that sortable's _intersectsWith uses
			sortable.positionAbs = draggable.positionAbs;
			sortable.helperProportions = draggable.helperProportions;
			sortable.offset.click = draggable.offset.click;

			if ( sortable._intersectsWith( sortable.containerCache ) ) {
				innermostIntersecting = true;

				$.each( draggable.sortables, function() {

					// Copy over variables that sortable's _intersectsWith uses
					this.positionAbs = draggable.positionAbs;
					this.helperProportions = draggable.helperProportions;
					this.offset.click = draggable.offset.click;

					if ( this !== sortable &&
							this._intersectsWith( this.containerCache ) &&
							$.contains( sortable.element[ 0 ], this.element[ 0 ] ) ) {
						innermostIntersecting = false;
					}

					return innermostIntersecting;
				} );
			}

			if ( innermostIntersecting ) {

				// If it intersects, we use a little isOver variable and set it
				// once,
				// so that the move-in stuff gets fired only once.
				if ( !sortable.isOver ) {
					sortable.isOver = 1;

					// Store draggable's parent in case we need to reappend to
					// it later.
					draggable._parent = ui.helper.parent();

					sortable.currentItem = ui.helper
						.appendTo( sortable.element )
						.data( "ui-sortable-item", true );

					// Store helper option to later restore it
					sortable.options._helper = sortable.options.helper;

					sortable.options.helper = function() {
						return ui.helper[ 0 ];
					};

					// Fire the start events of the sortable with our passed
					// browser event,
					// and our own helper (so it doesn't create a new one)
					event.target = sortable.currentItem[ 0 ];
					sortable._mouseCapture( event, true );
					sortable._mouseStart( event, true, true );

					// Because the browser event is way off the new appended
					// portlet,
					// modify necessary variables to reflect the changes
					sortable.offset.click.top = draggable.offset.click.top;
					sortable.offset.click.left = draggable.offset.click.left;
					sortable.offset.parent.left -= draggable.offset.parent.left -
						sortable.offset.parent.left;
					sortable.offset.parent.top -= draggable.offset.parent.top -
						sortable.offset.parent.top;

					draggable._trigger( "toSortable", event );

					// Inform draggable that the helper is in a valid drop zone,
					// used solely in the revert option to handle
					// "valid/invalid".
					draggable.dropped = sortable.element;

					// Need to refreshPositions of all sortables in the case
					// that
					// adding to one sortable changes the location of the other
					// sortables (#9675)
					$.each( draggable.sortables, function() {
						this.refreshPositions();
					} );

					// Hack so receive/update callbacks work (mostly)
					draggable.currentItem = draggable.element;
					sortable.fromOutside = draggable;
				}

				if ( sortable.currentItem ) {
					sortable._mouseDrag( event );

					// Copy the sortable's position because the draggable's can
					// potentially reflect
					// a relative position, while sortable is always absolute,
					// which the dragged
					// element has now become. (#8809)
					ui.position = sortable.position;
				}
			} else {

				// If it doesn't intersect with the sortable, and it intersected
				// before,
				// we fake the drag stop of the sortable, but make sure it
				// doesn't remove
				// the helper by using cancelHelperRemoval.
				if ( sortable.isOver ) {

					sortable.isOver = 0;
					sortable.cancelHelperRemoval = true;

					// Calling sortable's mouseStop would trigger a revert,
					// so revert must be temporarily false until after mouseStop
					// is called.
					sortable.options._revert = sortable.options.revert;
					sortable.options.revert = false;

					sortable._trigger( "out", event, sortable._uiHash( sortable ) );
					sortable._mouseStop( event, true );

					// Restore sortable behaviors that were modfied
					// when the draggable entered the sortable area (#9481)
					sortable.options.revert = sortable.options._revert;
					sortable.options.helper = sortable.options._helper;

					if ( sortable.placeholder ) {
						sortable.placeholder.remove();
					}

					// Restore and recalculate the draggable's offset
					// considering the sortable
					// may have modified them in unexpected ways. (#8809,
					// #10669)
					ui.helper.appendTo( draggable._parent );
					draggable._refreshOffsets( event );
					ui.position = draggable._generatePosition( event, true );

					draggable._trigger( "fromSortable", event );

					// Inform draggable that the helper is no longer in a valid
					// drop zone
					draggable.dropped = false;

					// Need to refreshPositions of all sortables just in case
					// removing
					// from one sortable changes the location of other sortables
					// (#9675)
					$.each( draggable.sortables, function() {
						this.refreshPositions();
					} );
				}
			}
		} );
	}
} );

$.ui.plugin.add( "draggable", "cursor", {
	start: function( event, ui, instance ) {
		var t = $( "body" ),
			o = instance.options;

		if ( t.css( "cursor" ) ) {
			o._cursor = t.css( "cursor" );
		}
		t.css( "cursor", o.cursor );
	},
	stop: function( event, ui, instance ) {
		var o = instance.options;
		if ( o._cursor ) {
			$( "body" ).css( "cursor", o._cursor );
		}
	}
} );

$.ui.plugin.add( "draggable", "opacity", {
	start: function( event, ui, instance ) {
		var t = $( ui.helper ),
			o = instance.options;
		if ( t.css( "opacity" ) ) {
			o._opacity = t.css( "opacity" );
		}
		t.css( "opacity", o.opacity );
	},
	stop: function( event, ui, instance ) {
		var o = instance.options;
		if ( o._opacity ) {
			$( ui.helper ).css( "opacity", o._opacity );
		}
	}
} );

$.ui.plugin.add( "draggable", "scroll", {
	start: function( event, ui, i ) {
		if ( !i.scrollParentNotHidden ) {
			i.scrollParentNotHidden = i.helper.scrollParent( false );
		}

		if ( i.scrollParentNotHidden[ 0 ] !== i.document[ 0 ] &&
				i.scrollParentNotHidden[ 0 ].tagName !== "HTML" ) {
			i.overflowOffset = i.scrollParentNotHidden.offset();
		}
	},
	drag: function( event, ui, i  ) {

		var o = i.options,
			scrolled = false,
			scrollParent = i.scrollParentNotHidden[ 0 ],
			document = i.document[ 0 ];

		if ( scrollParent !== document && scrollParent.tagName !== "HTML" ) {
			if ( !o.axis || o.axis !== "x" ) {
				if ( ( i.overflowOffset.top + scrollParent.offsetHeight ) - event.pageY <
						o.scrollSensitivity ) {
					scrollParent.scrollTop = scrolled = scrollParent.scrollTop + o.scrollSpeed;
				} else if ( event.pageY - i.overflowOffset.top < o.scrollSensitivity ) {
					scrollParent.scrollTop = scrolled = scrollParent.scrollTop - o.scrollSpeed;
				}
			}

			if ( !o.axis || o.axis !== "y" ) {
				if ( ( i.overflowOffset.left + scrollParent.offsetWidth ) - event.pageX <
						o.scrollSensitivity ) {
					scrollParent.scrollLeft = scrolled = scrollParent.scrollLeft + o.scrollSpeed;
				} else if ( event.pageX - i.overflowOffset.left < o.scrollSensitivity ) {
					scrollParent.scrollLeft = scrolled = scrollParent.scrollLeft - o.scrollSpeed;
				}
			}

		} else {

			if ( !o.axis || o.axis !== "x" ) {
				if ( event.pageY - $( document ).scrollTop() < o.scrollSensitivity ) {
					scrolled = $( document ).scrollTop( $( document ).scrollTop() - o.scrollSpeed );
				} else if ( $( window ).height() - ( event.pageY - $( document ).scrollTop() ) <
						o.scrollSensitivity ) {
					scrolled = $( document ).scrollTop( $( document ).scrollTop() + o.scrollSpeed );
				}
			}

			if ( !o.axis || o.axis !== "y" ) {
				if ( event.pageX - $( document ).scrollLeft() < o.scrollSensitivity ) {
					scrolled = $( document ).scrollLeft(
						$( document ).scrollLeft() - o.scrollSpeed
					);
				} else if ( $( window ).width() - ( event.pageX - $( document ).scrollLeft() ) <
						o.scrollSensitivity ) {
					scrolled = $( document ).scrollLeft(
						$( document ).scrollLeft() + o.scrollSpeed
					);
				}
			}

		}

		if ( scrolled !== false && $.ui.ddmanager && !o.dropBehaviour ) {
			$.ui.ddmanager.prepareOffsets( i, event );
		}

	}
} );

$.ui.plugin.add( "draggable", "snap", {
	start: function( event, ui, i ) {

		var o = i.options;

		i.snapElements = [];

		$( o.snap.constructor !== String ? ( o.snap.items || ":data(ui-draggable)" ) : o.snap )
			.each( function() {
				var $t = $( this ),
					$o = $t.offset();
				if ( this !== i.element[ 0 ] ) {
					i.snapElements.push( {
						item: this,
						width: $t.outerWidth(), height: $t.outerHeight(),
						top: $o.top, left: $o.left
					} );
				}
			} );

	},
	drag: function( event, ui, inst ) {

		var ts, bs, ls, rs, l, r, t, b, i, first,
			o = inst.options,
			d = o.snapTolerance,
			x1 = ui.offset.left, x2 = x1 + inst.helperProportions.width,
			y1 = ui.offset.top, y2 = y1 + inst.helperProportions.height;

		for ( i = inst.snapElements.length - 1; i >= 0; i-- ) {

			l = inst.snapElements[ i ].left - inst.margins.left;
			r = l + inst.snapElements[ i ].width;
			t = inst.snapElements[ i ].top - inst.margins.top;
			b = t + inst.snapElements[ i ].height;

			if ( x2 < l - d || x1 > r + d || y2 < t - d || y1 > b + d ||
					!$.contains( inst.snapElements[ i ].item.ownerDocument,
					inst.snapElements[ i ].item ) ) {
				if ( inst.snapElements[ i ].snapping ) {
					( inst.options.snap.release &&
						inst.options.snap.release.call(
							inst.element,
							event,
							$.extend( inst._uiHash(), { snapItem: inst.snapElements[ i ].item } )
						) );
				}
				inst.snapElements[ i ].snapping = false;
				continue;
			}

			if ( o.snapMode !== "inner" ) {
				ts = Math.abs( t - y2 ) <= d;
				bs = Math.abs( b - y1 ) <= d;
				ls = Math.abs( l - x2 ) <= d;
				rs = Math.abs( r - x1 ) <= d;
				if ( ts ) {
					ui.position.top = inst._convertPositionTo( "relative", {
						top: t - inst.helperProportions.height,
						left: 0
					} ).top;
				}
				if ( bs ) {
					ui.position.top = inst._convertPositionTo( "relative", {
						top: b,
						left: 0
					} ).top;
				}
				if ( ls ) {
					ui.position.left = inst._convertPositionTo( "relative", {
						top: 0,
						left: l - inst.helperProportions.width
					} ).left;
				}
				if ( rs ) {
					ui.position.left = inst._convertPositionTo( "relative", {
						top: 0,
						left: r
					} ).left;
				}
			}

			first = ( ts || bs || ls || rs );

			if ( o.snapMode !== "outer" ) {
				ts = Math.abs( t - y1 ) <= d;
				bs = Math.abs( b - y2 ) <= d;
				ls = Math.abs( l - x1 ) <= d;
				rs = Math.abs( r - x2 ) <= d;
				if ( ts ) {
					ui.position.top = inst._convertPositionTo( "relative", {
						top: t,
						left: 0
					} ).top;
				}
				if ( bs ) {
					ui.position.top = inst._convertPositionTo( "relative", {
						top: b - inst.helperProportions.height,
						left: 0
					} ).top;
				}
				if ( ls ) {
					ui.position.left = inst._convertPositionTo( "relative", {
						top: 0,
						left: l
					} ).left;
				}
				if ( rs ) {
					ui.position.left = inst._convertPositionTo( "relative", {
						top: 0,
						left: r - inst.helperProportions.width
					} ).left;
				}
			}

			if ( !inst.snapElements[ i ].snapping && ( ts || bs || ls || rs || first ) ) {
				( inst.options.snap.snap &&
					inst.options.snap.snap.call(
						inst.element,
						event,
						$.extend( inst._uiHash(), {
							snapItem: inst.snapElements[ i ].item
						} ) ) );
			}
			inst.snapElements[ i ].snapping = ( ts || bs || ls || rs || first );

		}

	}
} );

$.ui.plugin.add( "draggable", "stack", {
	start: function( event, ui, instance ) {
		var min,
			o = instance.options,
			group = $.makeArray( $( o.stack ) ).sort( function( a, b ) {
				return ( parseInt( $( a ).css( "zIndex" ), 10 ) || 0 ) -
					( parseInt( $( b ).css( "zIndex" ), 10 ) || 0 );
			} );

		if ( !group.length ) { return; }

		min = parseInt( $( group[ 0 ] ).css( "zIndex" ), 10 ) || 0;
		$( group ).each( function( i ) {
			$( this ).css( "zIndex", min + i );
		} );
		this.css( "zIndex", ( min + group.length ) );
	}
} );

$.ui.plugin.add( "draggable", "zIndex", {
	start: function( event, ui, instance ) {
		var t = $( ui.helper ),
			o = instance.options;

		if ( t.css( "zIndex" ) ) {
			o._zIndex = t.css( "zIndex" );
		}
		t.css( "zIndex", o.zIndex );
	},
	stop: function( event, ui, instance ) {
		var o = instance.options;

		if ( o._zIndex ) {
			$( ui.helper ).css( "zIndex", o._zIndex );
		}
	}
} );

var widgetsDraggable = $.ui.draggable;


/*
 * ! jQuery UI Resizable 1.12.1 http://jqueryui.com
 * 
 * Copyright jQuery Foundation and other contributors Released under the MIT
 * license. http://jquery.org/license
 */

// >>label: Resizable
// >>group: Interactions
// >>description: Enables resize functionality for any element.
// >>docs: http://api.jqueryui.com/resizable/
// >>demos: http://jqueryui.com/resizable/
// >>css.structure: ../../themes/base/core.css
// >>css.structure: ../../themes/base/resizable.css
// >>css.theme: ../../themes/base/theme.css



$.widget( "ui.resizable", $.ui.mouse, {
	version: "1.12.1",
	widgetEventPrefix: "resize",
	options: {
		alsoResize: false,
		animate: false,
		animateDuration: "slow",
		animateEasing: "swing",
		aspectRatio: false,
		autoHide: false,
		classes: {
			"ui-resizable-se": "ui-icon ui-icon-gripsmall-diagonal-se"
		},
		containment: false,
		ghost: false,
		grid: false,
		handles: "e,s,se",
		helper: false,
		maxHeight: null,
		maxWidth: null,
		minHeight: 10,
		minWidth: 10,

		// See #7960
		zIndex: 90,

		// Callbacks
		resize: null,
		start: null,
		stop: null
	},

	_num: function( value ) {
		return parseFloat( value ) || 0;
	},

	_isNumber: function( value ) {
		return !isNaN( parseFloat( value ) );
	},

	_hasScroll: function( el, a ) {

		if ( $( el ).css( "overflow" ) === "hidden" ) {
			return false;
		}

		var scroll = ( a && a === "left" ) ? "scrollLeft" : "scrollTop",
			has = false;

		if ( el[ scroll ] > 0 ) {
			return true;
		}

		// TODO: determine which cases actually cause this to happen
		// if the element doesn't have the scroll set, see if it's possible to
		// set the scroll
		el[ scroll ] = 1;
		has = ( el[ scroll ] > 0 );
		el[ scroll ] = 0;
		return has;
	},

	_create: function() {

		var margins,
			o = this.options,
			that = this;
		this._addClass( "ui-resizable" );

		$.extend( this, {
			_aspectRatio: !!( o.aspectRatio ),
			aspectRatio: o.aspectRatio,
			originalElement: this.element,
			_proportionallyResizeElements: [],
			_helper: o.helper || o.ghost || o.animate ? o.helper || "ui-resizable-helper" : null
		} );

		// Wrap the element if it cannot hold child nodes
		if ( this.element[ 0 ].nodeName.match( /^(canvas|textarea|input|select|button|img)$/i ) ) {

			this.element.wrap(
				$( "<div class='ui-wrapper' style='overflow: hidden;'></div>" ).css( {
					position: this.element.css( "position" ),
					width: this.element.outerWidth(),
					height: this.element.outerHeight(),
					top: this.element.css( "top" ),
					left: this.element.css( "left" )
				} )
			);

			this.element = this.element.parent().data(
				"ui-resizable", this.element.resizable( "instance" )
			);

			this.elementIsWrapper = true;

			margins = {
				marginTop: this.originalElement.css( "marginTop" ),
				marginRight: this.originalElement.css( "marginRight" ),
				marginBottom: this.originalElement.css( "marginBottom" ),
				marginLeft: this.originalElement.css( "marginLeft" )
			};

			this.element.css( margins );
			this.originalElement.css( "margin", 0 );

			// support: Safari
			// Prevent Safari textarea resize
			this.originalResizeStyle = this.originalElement.css( "resize" );
			this.originalElement.css( "resize", "none" );

			this._proportionallyResizeElements.push( this.originalElement.css( {
				position: "static",
				zoom: 1,
				display: "block"
			} ) );

			// Support: IE9
			// avoid IE jump (hard set the margin)
			this.originalElement.css( margins );

			this._proportionallyResize();
		}

		this._setupHandles();

		if ( o.autoHide ) {
			$( this.element )
				.on( "mouseenter", function() {
					if ( o.disabled ) {
						return;
					}
					that._removeClass( "ui-resizable-autohide" );
					that._handles.show();
				} )
				.on( "mouseleave", function() {
					if ( o.disabled ) {
						return;
					}
					if ( !that.resizing ) {
						that._addClass( "ui-resizable-autohide" );
						that._handles.hide();
					}
				} );
		}

		this._mouseInit();
	},

	_destroy: function() {

		this._mouseDestroy();

		var wrapper,
			_destroy = function( exp ) {
				$( exp )
					.removeData( "resizable" )
					.removeData( "ui-resizable" )
					.off( ".resizable" )
					.find( ".ui-resizable-handle" )
						.remove();
			};

		// TODO: Unwrap at same DOM position
		if ( this.elementIsWrapper ) {
			_destroy( this.element );
			wrapper = this.element;
			this.originalElement.css( {
				position: wrapper.css( "position" ),
				width: wrapper.outerWidth(),
				height: wrapper.outerHeight(),
				top: wrapper.css( "top" ),
				left: wrapper.css( "left" )
			} ).insertAfter( wrapper );
			wrapper.remove();
		}

		this.originalElement.css( "resize", this.originalResizeStyle );
		_destroy( this.originalElement );

		return this;
	},

	_setOption: function( key, value ) {
		this._super( key, value );

		switch ( key ) {
		case "handles":
			this._removeHandles();
			this._setupHandles();
			break;
		default:
			break;
		}
	},

	_setupHandles: function() {
		var o = this.options, handle, i, n, hname, axis, that = this;
		this.handles = o.handles ||
			( !$( ".ui-resizable-handle", this.element ).length ?
				"e,s,se" : {
					n: ".ui-resizable-n",
					e: ".ui-resizable-e",
					s: ".ui-resizable-s",
					w: ".ui-resizable-w",
					se: ".ui-resizable-se",
					sw: ".ui-resizable-sw",
					ne: ".ui-resizable-ne",
					nw: ".ui-resizable-nw"
				} );

		this._handles = $();
		if ( this.handles.constructor === String ) {

			if ( this.handles === "all" ) {
				this.handles = "n,e,s,w,se,sw,ne,nw";
			}

			n = this.handles.split( "," );
			this.handles = {};

			for ( i = 0; i < n.length; i++ ) {

				handle = $.trim( n[ i ] );
				hname = "ui-resizable-" + handle;
				axis = $( "<div>" );
				this._addClass( axis, "ui-resizable-handle " + hname );

				axis.css( { zIndex: o.zIndex } );

				this.handles[ handle ] = ".ui-resizable-" + handle;
				this.element.append( axis );
			}

		}

		this._renderAxis = function( target ) {

			var i, axis, padPos, padWrapper;

			target = target || this.element;

			for ( i in this.handles ) {

				if ( this.handles[ i ].constructor === String ) {
					this.handles[ i ] = this.element.children( this.handles[ i ] ).first().show();
				} else if ( this.handles[ i ].jquery || this.handles[ i ].nodeType ) {
					this.handles[ i ] = $( this.handles[ i ] );
					this._on( this.handles[ i ], { "mousedown": that._mouseDown } );
				}

				if ( this.elementIsWrapper &&
						this.originalElement[ 0 ]
							.nodeName
							.match( /^(textarea|input|select|button)$/i ) ) {
					axis = $( this.handles[ i ], this.element );

					padWrapper = /sw|ne|nw|se|n|s/.test( i ) ?
						axis.outerHeight() :
						axis.outerWidth();

					padPos = [ "padding",
						/ne|nw|n/.test( i ) ? "Top" :
						/se|sw|s/.test( i ) ? "Bottom" :
						/^e$/.test( i ) ? "Right" : "Left" ].join( "" );

					target.css( padPos, padWrapper );

					this._proportionallyResize();
				}

				this._handles = this._handles.add( this.handles[ i ] );
			}
		};

		// TODO: make renderAxis a prototype function
		this._renderAxis( this.element );

		this._handles = this._handles.add( this.element.find( ".ui-resizable-handle" ) );
		this._handles.disableSelection();

		this._handles.on( "mouseover", function() {
			if ( !that.resizing ) {
				if ( this.className ) {
					axis = this.className.match( /ui-resizable-(se|sw|ne|nw|n|e|s|w)/i );
				}
				that.axis = axis && axis[ 1 ] ? axis[ 1 ] : "se";
			}
		} );

		if ( o.autoHide ) {
			this._handles.hide();
			this._addClass( "ui-resizable-autohide" );
		}
	},

	_removeHandles: function() {
		this._handles.remove();
	},

	_mouseCapture: function( event ) {
		var i, handle,
			capture = false;

		for ( i in this.handles ) {
			handle = $( this.handles[ i ] )[ 0 ];
			if ( handle === event.target || $.contains( handle, event.target ) ) {
				capture = true;
			}
		}

		return !this.options.disabled && capture;
	},

	_mouseStart: function( event ) {

		var curleft, curtop, cursor,
			o = this.options,
			el = this.element;

		this.resizing = true;

		this._renderProxy();

		curleft = this._num( this.helper.css( "left" ) );
		curtop = this._num( this.helper.css( "top" ) );

		if ( o.containment ) {
			curleft += $( o.containment ).scrollLeft() || 0;
			curtop += $( o.containment ).scrollTop() || 0;
		}

		this.offset = this.helper.offset();
		this.position = { left: curleft, top: curtop };

		this.size = this._helper ? {
				width: this.helper.width(),
				height: this.helper.height()
			} : {
				width: el.width(),
				height: el.height()
			};

		this.originalSize = this._helper ? {
				width: el.outerWidth(),
				height: el.outerHeight()
			} : {
				width: el.width(),
				height: el.height()
			};

		this.sizeDiff = {
			width: el.outerWidth() - el.width(),
			height: el.outerHeight() - el.height()
		};

		this.originalPosition = { left: curleft, top: curtop };
		this.originalMousePosition = { left: event.pageX, top: event.pageY };

		this.aspectRatio = ( typeof o.aspectRatio === "number" ) ?
			o.aspectRatio :
			( ( this.originalSize.width / this.originalSize.height ) || 1 );

		cursor = $( ".ui-resizable-" + this.axis ).css( "cursor" );
		$( "body" ).css( "cursor", cursor === "auto" ? this.axis + "-resize" : cursor );

		this._addClass( "ui-resizable-resizing" );
		this._propagate( "start", event );
		return true;
	},

	_mouseDrag: function( event ) {

		var data, props,
			smp = this.originalMousePosition,
			a = this.axis,
			dx = ( event.pageX - smp.left ) || 0,
			dy = ( event.pageY - smp.top ) || 0,
			trigger = this._change[ a ];

		this._updatePrevProperties();

		if ( !trigger ) {
			return false;
		}

		data = trigger.apply( this, [ event, dx, dy ] );

		this._updateVirtualBoundaries( event.shiftKey );
		if ( this._aspectRatio || event.shiftKey ) {
			data = this._updateRatio( data, event );
		}

		data = this._respectSize( data, event );

		this._updateCache( data );

		this._propagate( "resize", event );

		props = this._applyChanges();

		if ( !this._helper && this._proportionallyResizeElements.length ) {
			this._proportionallyResize();
		}

		if ( !$.isEmptyObject( props ) ) {
			this._updatePrevProperties();
			this._trigger( "resize", event, this.ui() );
			this._applyChanges();
		}

		return false;
	},

	_mouseStop: function( event ) {

		this.resizing = false;
		var pr, ista, soffseth, soffsetw, s, left, top,
			o = this.options, that = this;

		if ( this._helper ) {

			pr = this._proportionallyResizeElements;
			ista = pr.length && ( /textarea/i ).test( pr[ 0 ].nodeName );
			soffseth = ista && this._hasScroll( pr[ 0 ], "left" ) ? 0 : that.sizeDiff.height;
			soffsetw = ista ? 0 : that.sizeDiff.width;

			s = {
				width: ( that.helper.width()  - soffsetw ),
				height: ( that.helper.height() - soffseth )
			};
			left = ( parseFloat( that.element.css( "left" ) ) +
				( that.position.left - that.originalPosition.left ) ) || null;
			top = ( parseFloat( that.element.css( "top" ) ) +
				( that.position.top - that.originalPosition.top ) ) || null;

			if ( !o.animate ) {
				this.element.css( $.extend( s, { top: top, left: left } ) );
			}

			that.helper.height( that.size.height );
			that.helper.width( that.size.width );

			if ( this._helper && !o.animate ) {
				this._proportionallyResize();
			}
		}

		$( "body" ).css( "cursor", "auto" );

		this._removeClass( "ui-resizable-resizing" );

		this._propagate( "stop", event );

		if ( this._helper ) {
			this.helper.remove();
		}

		return false;

	},

	_updatePrevProperties: function() {
		this.prevPosition = {
			top: this.position.top,
			left: this.position.left
		};
		this.prevSize = {
			width: this.size.width,
			height: this.size.height
		};
	},

	_applyChanges: function() {
		var props = {};

		if ( this.position.top !== this.prevPosition.top ) {
			props.top = this.position.top + "px";
		}
		if ( this.position.left !== this.prevPosition.left ) {
			props.left = this.position.left + "px";
		}
		if ( this.size.width !== this.prevSize.width ) {
			props.width = this.size.width + "px";
		}
		if ( this.size.height !== this.prevSize.height ) {
			props.height = this.size.height + "px";
		}

		this.helper.css( props );

		return props;
	},

	_updateVirtualBoundaries: function( forceAspectRatio ) {
		var pMinWidth, pMaxWidth, pMinHeight, pMaxHeight, b,
			o = this.options;

		b = {
			minWidth: this._isNumber( o.minWidth ) ? o.minWidth : 0,
			maxWidth: this._isNumber( o.maxWidth ) ? o.maxWidth : Infinity,
			minHeight: this._isNumber( o.minHeight ) ? o.minHeight : 0,
			maxHeight: this._isNumber( o.maxHeight ) ? o.maxHeight : Infinity
		};

		if ( this._aspectRatio || forceAspectRatio ) {
			pMinWidth = b.minHeight * this.aspectRatio;
			pMinHeight = b.minWidth / this.aspectRatio;
			pMaxWidth = b.maxHeight * this.aspectRatio;
			pMaxHeight = b.maxWidth / this.aspectRatio;

			if ( pMinWidth > b.minWidth ) {
				b.minWidth = pMinWidth;
			}
			if ( pMinHeight > b.minHeight ) {
				b.minHeight = pMinHeight;
			}
			if ( pMaxWidth < b.maxWidth ) {
				b.maxWidth = pMaxWidth;
			}
			if ( pMaxHeight < b.maxHeight ) {
				b.maxHeight = pMaxHeight;
			}
		}
		this._vBoundaries = b;
	},

	_updateCache: function( data ) {
		this.offset = this.helper.offset();
		if ( this._isNumber( data.left ) ) {
			this.position.left = data.left;
		}
		if ( this._isNumber( data.top ) ) {
			this.position.top = data.top;
		}
		if ( this._isNumber( data.height ) ) {
			this.size.height = data.height;
		}
		if ( this._isNumber( data.width ) ) {
			this.size.width = data.width;
		}
	},

	_updateRatio: function( data ) {

		var cpos = this.position,
			csize = this.size,
			a = this.axis;

		if ( this._isNumber( data.height ) ) {
			data.width = ( data.height * this.aspectRatio );
		} else if ( this._isNumber( data.width ) ) {
			data.height = ( data.width / this.aspectRatio );
		}

		if ( a === "sw" ) {
			data.left = cpos.left + ( csize.width - data.width );
			data.top = null;
		}
		if ( a === "nw" ) {
			data.top = cpos.top + ( csize.height - data.height );
			data.left = cpos.left + ( csize.width - data.width );
		}

		return data;
	},

	_respectSize: function( data ) {

		var o = this._vBoundaries,
			a = this.axis,
			ismaxw = this._isNumber( data.width ) && o.maxWidth && ( o.maxWidth < data.width ),
			ismaxh = this._isNumber( data.height ) && o.maxHeight && ( o.maxHeight < data.height ),
			isminw = this._isNumber( data.width ) && o.minWidth && ( o.minWidth > data.width ),
			isminh = this._isNumber( data.height ) && o.minHeight && ( o.minHeight > data.height ),
			dw = this.originalPosition.left + this.originalSize.width,
			dh = this.originalPosition.top + this.originalSize.height,
			cw = /sw|nw|w/.test( a ), ch = /nw|ne|n/.test( a );
		if ( isminw ) {
			data.width = o.minWidth;
		}
		if ( isminh ) {
			data.height = o.minHeight;
		}
		if ( ismaxw ) {
			data.width = o.maxWidth;
		}
		if ( ismaxh ) {
			data.height = o.maxHeight;
		}

		if ( isminw && cw ) {
			data.left = dw - o.minWidth;
		}
		if ( ismaxw && cw ) {
			data.left = dw - o.maxWidth;
		}
		if ( isminh && ch ) {
			data.top = dh - o.minHeight;
		}
		if ( ismaxh && ch ) {
			data.top = dh - o.maxHeight;
		}

		// Fixing jump error on top/left - bug #2330
		if ( !data.width && !data.height && !data.left && data.top ) {
			data.top = null;
		} else if ( !data.width && !data.height && !data.top && data.left ) {
			data.left = null;
		}

		return data;
	},

	_getPaddingPlusBorderDimensions: function( element ) {
		var i = 0,
			widths = [],
			borders = [
				element.css( "borderTopWidth" ),
				element.css( "borderRightWidth" ),
				element.css( "borderBottomWidth" ),
				element.css( "borderLeftWidth" )
			],
			paddings = [
				element.css( "paddingTop" ),
				element.css( "paddingRight" ),
				element.css( "paddingBottom" ),
				element.css( "paddingLeft" )
			];

		for ( ; i < 4; i++ ) {
			widths[ i ] = ( parseFloat( borders[ i ] ) || 0 );
			widths[ i ] += ( parseFloat( paddings[ i ] ) || 0 );
		}

		return {
			height: widths[ 0 ] + widths[ 2 ],
			width: widths[ 1 ] + widths[ 3 ]
		};
	},

	_proportionallyResize: function() {

		if ( !this._proportionallyResizeElements.length ) {
			return;
		}

		var prel,
			i = 0,
			element = this.helper || this.element;

		for ( ; i < this._proportionallyResizeElements.length; i++ ) {

			prel = this._proportionallyResizeElements[ i ];

			// TODO: Seems like a bug to cache this.outerDimensions
			// considering that we are in a loop.
			if ( !this.outerDimensions ) {
				this.outerDimensions = this._getPaddingPlusBorderDimensions( prel );
			}

			prel.css( {
				height: ( element.height() - this.outerDimensions.height ) || 0,
				width: ( element.width() - this.outerDimensions.width ) || 0
			} );

		}

	},

	_renderProxy: function() {

		var el = this.element, o = this.options;
		this.elementOffset = el.offset();

		if ( this._helper ) {

			this.helper = this.helper || $( "<div style='overflow:hidden;'></div>" );

			this._addClass( this.helper, this._helper );
			this.helper.css( {
				width: this.element.outerWidth(),
				height: this.element.outerHeight(),
				position: "absolute",
				left: this.elementOffset.left + "px",
				top: this.elementOffset.top + "px",
				zIndex: ++o.zIndex // TODO: Don't modify option
			} );

			this.helper
				.appendTo( "body" )
				.disableSelection();

		} else {
			this.helper = this.element;
		}

	},

	_change: {
		e: function( event, dx ) {
			return { width: this.originalSize.width + dx };
		},
		w: function( event, dx ) {
			var cs = this.originalSize, sp = this.originalPosition;
			return { left: sp.left + dx, width: cs.width - dx };
		},
		n: function( event, dx, dy ) {
			var cs = this.originalSize, sp = this.originalPosition;
			return { top: sp.top + dy, height: cs.height - dy };
		},
		s: function( event, dx, dy ) {
			return { height: this.originalSize.height + dy };
		},
		se: function( event, dx, dy ) {
			return $.extend( this._change.s.apply( this, arguments ),
				this._change.e.apply( this, [ event, dx, dy ] ) );
		},
		sw: function( event, dx, dy ) {
			return $.extend( this._change.s.apply( this, arguments ),
				this._change.w.apply( this, [ event, dx, dy ] ) );
		},
		ne: function( event, dx, dy ) {
			return $.extend( this._change.n.apply( this, arguments ),
				this._change.e.apply( this, [ event, dx, dy ] ) );
		},
		nw: function( event, dx, dy ) {
			return $.extend( this._change.n.apply( this, arguments ),
				this._change.w.apply( this, [ event, dx, dy ] ) );
		}
	},

	_propagate: function( n, event ) {
		$.ui.plugin.call( this, n, [ event, this.ui() ] );
		( n !== "resize" && this._trigger( n, event, this.ui() ) );
	},

	plugins: {},

	ui: function() {
		return {
			originalElement: this.originalElement,
			element: this.element,
			helper: this.helper,
			position: this.position,
			size: this.size,
			originalSize: this.originalSize,
			originalPosition: this.originalPosition
		};
	}

} );

/*
 * Resizable Extensions
 */

$.ui.plugin.add( "resizable", "animate", {

	stop: function( event ) {
		var that = $( this ).resizable( "instance" ),
			o = that.options,
			pr = that._proportionallyResizeElements,
			ista = pr.length && ( /textarea/i ).test( pr[ 0 ].nodeName ),
			soffseth = ista && that._hasScroll( pr[ 0 ], "left" ) ? 0 : that.sizeDiff.height,
			soffsetw = ista ? 0 : that.sizeDiff.width,
			style = {
				width: ( that.size.width - soffsetw ),
				height: ( that.size.height - soffseth )
			},
			left = ( parseFloat( that.element.css( "left" ) ) +
				( that.position.left - that.originalPosition.left ) ) || null,
			top = ( parseFloat( that.element.css( "top" ) ) +
				( that.position.top - that.originalPosition.top ) ) || null;

		that.element.animate(
			$.extend( style, top && left ? { top: top, left: left } : {} ), {
				duration: o.animateDuration,
				easing: o.animateEasing,
				step: function() {

					var data = {
						width: parseFloat( that.element.css( "width" ) ),
						height: parseFloat( that.element.css( "height" ) ),
						top: parseFloat( that.element.css( "top" ) ),
						left: parseFloat( that.element.css( "left" ) )
					};

					if ( pr && pr.length ) {
						$( pr[ 0 ] ).css( { width: data.width, height: data.height } );
					}

					// Propagating resize, and updating values for each
					// animation step
					that._updateCache( data );
					that._propagate( "resize", event );

				}
			}
		);
	}

} );

$.ui.plugin.add( "resizable", "containment", {

	start: function() {
		var element, p, co, ch, cw, width, height,
			that = $( this ).resizable( "instance" ),
			o = that.options,
			el = that.element,
			oc = o.containment,
			ce = ( oc instanceof $ ) ?
				oc.get( 0 ) :
				( /parent/.test( oc ) ) ? el.parent().get( 0 ) : oc;

		if ( !ce ) {
			return;
		}

		that.containerElement = $( ce );

		if ( /document/.test( oc ) || oc === document ) {
			that.containerOffset = {
				left: 0,
				top: 0
			};
			that.containerPosition = {
				left: 0,
				top: 0
			};

			that.parentData = {
				element: $( document ),
				left: 0,
				top: 0,
				width: $( document ).width(),
				height: $( document ).height() || document.body.parentNode.scrollHeight
			};
		} else {
			element = $( ce );
			p = [];
			$( [ "Top", "Right", "Left", "Bottom" ] ).each( function( i, name ) {
				p[ i ] = that._num( element.css( "padding" + name ) );
			} );

			that.containerOffset = element.offset();
			that.containerPosition = element.position();
			that.containerSize = {
				height: ( element.innerHeight() - p[ 3 ] ),
				width: ( element.innerWidth() - p[ 1 ] )
			};

			co = that.containerOffset;
			ch = that.containerSize.height;
			cw = that.containerSize.width;
			width = ( that._hasScroll ( ce, "left" ) ? ce.scrollWidth : cw );
			height = ( that._hasScroll ( ce ) ? ce.scrollHeight : ch ) ;

			that.parentData = {
				element: ce,
				left: co.left,
				top: co.top,
				width: width,
				height: height
			};
		}
	},

	resize: function( event ) {
		var woset, hoset, isParent, isOffsetRelative,
			that = $( this ).resizable( "instance" ),
			o = that.options,
			co = that.containerOffset,
			cp = that.position,
			pRatio = that._aspectRatio || event.shiftKey,
			cop = {
				top: 0,
				left: 0
			},
			ce = that.containerElement,
			continueResize = true;

		if ( ce[ 0 ] !== document && ( /static/ ).test( ce.css( "position" ) ) ) {
			cop = co;
		}

		if ( cp.left < ( that._helper ? co.left : 0 ) ) {
			that.size.width = that.size.width +
				( that._helper ?
					( that.position.left - co.left ) :
					( that.position.left - cop.left ) );

			if ( pRatio ) {
				that.size.height = that.size.width / that.aspectRatio;
				continueResize = false;
			}
			that.position.left = o.helper ? co.left : 0;
		}

		if ( cp.top < ( that._helper ? co.top : 0 ) ) {
			that.size.height = that.size.height +
				( that._helper ?
					( that.position.top - co.top ) :
					that.position.top );

			if ( pRatio ) {
				that.size.width = that.size.height * that.aspectRatio;
				continueResize = false;
			}
			that.position.top = that._helper ? co.top : 0;
		}

		isParent = that.containerElement.get( 0 ) === that.element.parent().get( 0 );
		isOffsetRelative = /relative|absolute/.test( that.containerElement.css( "position" ) );

		if ( isParent && isOffsetRelative ) {
			that.offset.left = that.parentData.left + that.position.left;
			that.offset.top = that.parentData.top + that.position.top;
		} else {
			that.offset.left = that.element.offset().left;
			that.offset.top = that.element.offset().top;
		}

		woset = Math.abs( that.sizeDiff.width +
			( that._helper ?
				that.offset.left - cop.left :
				( that.offset.left - co.left ) ) );

		hoset = Math.abs( that.sizeDiff.height +
			( that._helper ?
				that.offset.top - cop.top :
				( that.offset.top - co.top ) ) );

		if ( woset + that.size.width >= that.parentData.width ) {
			that.size.width = that.parentData.width - woset;
			if ( pRatio ) {
				that.size.height = that.size.width / that.aspectRatio;
				continueResize = false;
			}
		}

		if ( hoset + that.size.height >= that.parentData.height ) {
			that.size.height = that.parentData.height - hoset;
			if ( pRatio ) {
				that.size.width = that.size.height * that.aspectRatio;
				continueResize = false;
			}
		}

		if ( !continueResize ) {
			that.position.left = that.prevPosition.left;
			that.position.top = that.prevPosition.top;
			that.size.width = that.prevSize.width;
			that.size.height = that.prevSize.height;
		}
	},

	stop: function() {
		var that = $( this ).resizable( "instance" ),
			o = that.options,
			co = that.containerOffset,
			cop = that.containerPosition,
			ce = that.containerElement,
			helper = $( that.helper ),
			ho = helper.offset(),
			w = helper.outerWidth() - that.sizeDiff.width,
			h = helper.outerHeight() - that.sizeDiff.height;

		if ( that._helper && !o.animate && ( /relative/ ).test( ce.css( "position" ) ) ) {
			$( this ).css( {
				left: ho.left - cop.left - co.left,
				width: w,
				height: h
			} );
		}

		if ( that._helper && !o.animate && ( /static/ ).test( ce.css( "position" ) ) ) {
			$( this ).css( {
				left: ho.left - cop.left - co.left,
				width: w,
				height: h
			} );
		}
	}
} );

$.ui.plugin.add( "resizable", "alsoResize", {

	start: function() {
		var that = $( this ).resizable( "instance" ),
			o = that.options;

		$( o.alsoResize ).each( function() {
			var el = $( this );
			el.data( "ui-resizable-alsoresize", {
				width: parseFloat( el.width() ), height: parseFloat( el.height() ),
				left: parseFloat( el.css( "left" ) ), top: parseFloat( el.css( "top" ) )
			} );
		} );
	},

	resize: function( event, ui ) {
		var that = $( this ).resizable( "instance" ),
			o = that.options,
			os = that.originalSize,
			op = that.originalPosition,
			delta = {
				height: ( that.size.height - os.height ) || 0,
				width: ( that.size.width - os.width ) || 0,
				top: ( that.position.top - op.top ) || 0,
				left: ( that.position.left - op.left ) || 0
			};

			$( o.alsoResize ).each( function() {
				var el = $( this ), start = $( this ).data( "ui-resizable-alsoresize" ), style = {},
					css = el.parents( ui.originalElement[ 0 ] ).length ?
							[ "width", "height" ] :
							[ "width", "height", "top", "left" ];

				$.each( css, function( i, prop ) {
					var sum = ( start[ prop ] || 0 ) + ( delta[ prop ] || 0 );
					if ( sum && sum >= 0 ) {
						style[ prop ] = sum || null;
					}
				} );

				el.css( style );
			} );
	},

	stop: function() {
		$( this ).removeData( "ui-resizable-alsoresize" );
	}
} );

$.ui.plugin.add( "resizable", "ghost", {

	start: function() {

		var that = $( this ).resizable( "instance" ), cs = that.size;

		that.ghost = that.originalElement.clone();
		that.ghost.css( {
			opacity: 0.25,
			display: "block",
			position: "relative",
			height: cs.height,
			width: cs.width,
			margin: 0,
			left: 0,
			top: 0
		} );

		that._addClass( that.ghost, "ui-resizable-ghost" );

		// DEPRECATED
		// TODO: remove after 1.12
		if ( $.uiBackCompat !== false && typeof that.options.ghost === "string" ) {

			// Ghost option
			that.ghost.addClass( this.options.ghost );
		}

		that.ghost.appendTo( that.helper );

	},

	resize: function() {
		var that = $( this ).resizable( "instance" );
		if ( that.ghost ) {
			that.ghost.css( {
				position: "relative",
				height: that.size.height,
				width: that.size.width
			} );
		}
	},

	stop: function() {
		var that = $( this ).resizable( "instance" );
		if ( that.ghost && that.helper ) {
			that.helper.get( 0 ).removeChild( that.ghost.get( 0 ) );
		}
	}

} );

$.ui.plugin.add( "resizable", "grid", {

	resize: function() {
		var outerDimensions,
			that = $( this ).resizable( "instance" ),
			o = that.options,
			cs = that.size,
			os = that.originalSize,
			op = that.originalPosition,
			a = that.axis,
			grid = typeof o.grid === "number" ? [ o.grid, o.grid ] : o.grid,
			gridX = ( grid[ 0 ] || 1 ),
			gridY = ( grid[ 1 ] || 1 ),
			ox = Math.round( ( cs.width - os.width ) / gridX ) * gridX,
			oy = Math.round( ( cs.height - os.height ) / gridY ) * gridY,
			newWidth = os.width + ox,
			newHeight = os.height + oy,
			isMaxWidth = o.maxWidth && ( o.maxWidth < newWidth ),
			isMaxHeight = o.maxHeight && ( o.maxHeight < newHeight ),
			isMinWidth = o.minWidth && ( o.minWidth > newWidth ),
			isMinHeight = o.minHeight && ( o.minHeight > newHeight );

		o.grid = grid;

		if ( isMinWidth ) {
			newWidth += gridX;
		}
		if ( isMinHeight ) {
			newHeight += gridY;
		}
		if ( isMaxWidth ) {
			newWidth -= gridX;
		}
		if ( isMaxHeight ) {
			newHeight -= gridY;
		}

		if ( /^(se|s|e)$/.test( a ) ) {
			that.size.width = newWidth;
			that.size.height = newHeight;
		} else if ( /^(ne)$/.test( a ) ) {
			that.size.width = newWidth;
			that.size.height = newHeight;
			that.position.top = op.top - oy;
		} else if ( /^(sw)$/.test( a ) ) {
			that.size.width = newWidth;
			that.size.height = newHeight;
			that.position.left = op.left - ox;
		} else {
			if ( newHeight - gridY <= 0 || newWidth - gridX <= 0 ) {
				outerDimensions = that._getPaddingPlusBorderDimensions( this );
			}

			if ( newHeight - gridY > 0 ) {
				that.size.height = newHeight;
				that.position.top = op.top - oy;
			} else {
				newHeight = gridY - outerDimensions.height;
				that.size.height = newHeight;
				that.position.top = op.top + os.height - newHeight;
			}
			if ( newWidth - gridX > 0 ) {
				that.size.width = newWidth;
				that.position.left = op.left - ox;
			} else {
				newWidth = gridX - outerDimensions.width;
				that.size.width = newWidth;
				that.position.left = op.left + os.width - newWidth;
			}
		}
	}

} );

var widgetsResizable = $.ui.resizable;


/*
 * ! jQuery UI Sortable 1.12.1 http://jqueryui.com
 * 
 * Copyright jQuery Foundation and other contributors Released under the MIT
 * license. http://jquery.org/license
 */

// >>label: Sortable
// >>group: Interactions
// >>description: Enables items in a list to be sorted using the mouse.
// >>docs: http://api.jqueryui.com/sortable/
// >>demos: http://jqueryui.com/sortable/
// >>css.structure: ../../themes/base/sortable.css



var widgetsSortable = $.widget( "ui.sortable", $.ui.mouse, {
	version: "1.12.1",
	widgetEventPrefix: "sort",
	ready: false,
	options: {
		appendTo: "parent",
		axis: false,
		connectWith: false,
		containment: false,
		cursor: "auto",
		cursorAt: false,
		dropOnEmpty: true,
		forcePlaceholderSize: false,
		forceHelperSize: false,
		grid: false,
		handle: false,
		helper: "original",
		items: "> *",
		opacity: false,
		placeholder: false,
		revert: false,
		scroll: true,
		scrollSensitivity: 20,
		scrollSpeed: 20,
		scope: "default",
		tolerance: "intersect",
		zIndex: 1000,

		// Callbacks
		activate: null,
		beforeStop: null,
		change: null,
		deactivate: null,
		out: null,
		over: null,
		receive: null,
		remove: null,
		sort: null,
		start: null,
		stop: null,
		update: null
	},

	_isOverAxis: function( x, reference, size ) {
		return ( x >= reference ) && ( x < ( reference + size ) );
	},

	_isFloating: function( item ) {
		return ( /left|right/ ).test( item.css( "float" ) ) ||
			( /inline|table-cell/ ).test( item.css( "display" ) );
	},

	_create: function() {
		this.containerCache = {};
		this._addClass( "ui-sortable" );

		// Get the items
		this.refresh();

		// Let's determine the parent's offset
		this.offset = this.element.offset();

		// Initialize mouse events for interaction
		this._mouseInit();

		this._setHandleClassName();

		// We're ready to go
		this.ready = true;

	},

	_setOption: function( key, value ) {
		this._super( key, value );

		if ( key === "handle" ) {
			this._setHandleClassName();
		}
	},

	_setHandleClassName: function() {
		var that = this;
		this._removeClass( this.element.find( ".ui-sortable-handle" ), "ui-sortable-handle" );
		$.each( this.items, function() {
			that._addClass(
				this.instance.options.handle ?
					this.item.find( this.instance.options.handle ) :
					this.item,
				"ui-sortable-handle"
			);
		} );
	},

	_destroy: function() {
		this._mouseDestroy();

		for ( var i = this.items.length - 1; i >= 0; i-- ) {
			this.items[ i ].item.removeData( this.widgetName + "-item" );
		}

		return this;
	},

	_mouseCapture: function( event, overrideHandle ) {
		var currentItem = null,
			validHandle = false,
			that = this;

		if ( this.reverting ) {
			return false;
		}

		if ( this.options.disabled || this.options.type === "static" ) {
			return false;
		}

		// We have to refresh the items data once first
		this._refreshItems( event );

		// Find out if the clicked node (or one of its parents) is a actual item
		// in this.items
		$( event.target ).parents().each( function() {
			if ( $.data( this, that.widgetName + "-item" ) === that ) {
				currentItem = $( this );
				return false;
			}
		} );
		if ( $.data( event.target, that.widgetName + "-item" ) === that ) {
			currentItem = $( event.target );
		}

		if ( !currentItem ) {
			return false;
		}
		if ( this.options.handle && !overrideHandle ) {
			$( this.options.handle, currentItem ).find( "*" ).addBack().each( function() {
				if ( this === event.target ) {
					validHandle = true;
				}
			} );
			if ( !validHandle ) {
				return false;
			}
		}

		this.currentItem = currentItem;
		this._removeCurrentsFromItems();
		return true;

	},

	_mouseStart: function( event, overrideHandle, noActivation ) {

		var i, body,
			o = this.options;

		this.currentContainer = this;

		// We only need to call refreshPositions, because the refreshItems call
		// has been moved to
		// mouseCapture
		this.refreshPositions();

		// Create and append the visible helper
		this.helper = this._createHelper( event );

		// Cache the helper size
		this._cacheHelperProportions();

		/*
		 * - Position generation - This block generates everything position
		 * related - it's the core of draggables.
		 */

		// Cache the margins of the original element
		this._cacheMargins();

		// Get the next scrolling parent
		this.scrollParent = this.helper.scrollParent();

		// The element's absolute position on the page minus margins
		this.offset = this.currentItem.offset();
		this.offset = {
			top: this.offset.top - this.margins.top,
			left: this.offset.left - this.margins.left
		};

		$.extend( this.offset, {
			click: { // Where the click happened, relative to the element
				left: event.pageX - this.offset.left,
				top: event.pageY - this.offset.top
			},
			parent: this._getParentOffset(),

			// This is a relative to absolute position minus the actual position
			// calculation -
			// only used for relative positioned helper
			relative: this._getRelativeOffset()
		} );

		// Only after we got the offset, we can change the helper's position to
		// absolute
		// TODO: Still need to figure out a way to make relative sorting
		// possible
		this.helper.css( "position", "absolute" );
		this.cssPosition = this.helper.css( "position" );

		// Generate the original position
		this.originalPosition = this._generatePosition( event );
		this.originalPageX = event.pageX;
		this.originalPageY = event.pageY;

		// Adjust the mouse offset relative to the helper if "cursorAt" is
		// supplied
		( o.cursorAt && this._adjustOffsetFromHelper( o.cursorAt ) );

		// Cache the former DOM position
		this.domPosition = {
			prev: this.currentItem.prev()[ 0 ],
			parent: this.currentItem.parent()[ 0 ]
		};

		// If the helper is not the original, hide the original so it's not
		// playing any role during
		// the drag, won't cause anything bad this way
		if ( this.helper[ 0 ] !== this.currentItem[ 0 ] ) {
			this.currentItem.hide();
		}

		// Create the placeholder
		this._createPlaceholder();

		// Set a containment if given in the options
		if ( o.containment ) {
			this._setContainment();
		}

		if ( o.cursor && o.cursor !== "auto" ) { // cursor option
			body = this.document.find( "body" );

			// Support: IE
			this.storedCursor = body.css( "cursor" );
			body.css( "cursor", o.cursor );

			this.storedStylesheet =
				$( "<style>*{ cursor: " + o.cursor + " !important; }</style>" ).appendTo( body );
		}

		if ( o.opacity ) { // opacity option
			if ( this.helper.css( "opacity" ) ) {
				this._storedOpacity = this.helper.css( "opacity" );
			}
			this.helper.css( "opacity", o.opacity );
		}

		if ( o.zIndex ) { // zIndex option
			if ( this.helper.css( "zIndex" ) ) {
				this._storedZIndex = this.helper.css( "zIndex" );
			}
			this.helper.css( "zIndex", o.zIndex );
		}

		// Prepare scrolling
		if ( this.scrollParent[ 0 ] !== this.document[ 0 ] &&
				this.scrollParent[ 0 ].tagName !== "HTML" ) {
			this.overflowOffset = this.scrollParent.offset();
		}

		// Call callbacks
		this._trigger( "start", event, this._uiHash() );

		// Recache the helper size
		if ( !this._preserveHelperProportions ) {
			this._cacheHelperProportions();
		}

		// Post "activate" events to possible containers
		if ( !noActivation ) {
			for ( i = this.containers.length - 1; i >= 0; i-- ) {
				this.containers[ i ]._trigger( "activate", event, this._uiHash( this ) );
			}
		}

		// Prepare possible droppables
		if ( $.ui.ddmanager ) {
			$.ui.ddmanager.current = this;
		}

		if ( $.ui.ddmanager && !o.dropBehaviour ) {
			$.ui.ddmanager.prepareOffsets( this, event );
		}

		this.dragging = true;

		this._addClass( this.helper, "ui-sortable-helper" );

		// Execute the drag once - this causes the helper not to be
		// visiblebefore getting its
		// correct position
		this._mouseDrag( event );
		return true;

	},

	_mouseDrag: function( event ) {
		var i, item, itemElement, intersection,
			o = this.options,
			scrolled = false;

		// Compute the helpers position
		this.position = this._generatePosition( event );
		this.positionAbs = this._convertPositionTo( "absolute" );

		if ( !this.lastPositionAbs ) {
			this.lastPositionAbs = this.positionAbs;
		}

		// Do scrolling
		if ( this.options.scroll ) {
			if ( this.scrollParent[ 0 ] !== this.document[ 0 ] &&
					this.scrollParent[ 0 ].tagName !== "HTML" ) {

				if ( ( this.overflowOffset.top + this.scrollParent[ 0 ].offsetHeight ) -
						event.pageY < o.scrollSensitivity ) {
					this.scrollParent[ 0 ].scrollTop =
						scrolled = this.scrollParent[ 0 ].scrollTop + o.scrollSpeed;
				} else if ( event.pageY - this.overflowOffset.top < o.scrollSensitivity ) {
					this.scrollParent[ 0 ].scrollTop =
						scrolled = this.scrollParent[ 0 ].scrollTop - o.scrollSpeed;
				}

				if ( ( this.overflowOffset.left + this.scrollParent[ 0 ].offsetWidth ) -
						event.pageX < o.scrollSensitivity ) {
					this.scrollParent[ 0 ].scrollLeft = scrolled =
						this.scrollParent[ 0 ].scrollLeft + o.scrollSpeed;
				} else if ( event.pageX - this.overflowOffset.left < o.scrollSensitivity ) {
					this.scrollParent[ 0 ].scrollLeft = scrolled =
						this.scrollParent[ 0 ].scrollLeft - o.scrollSpeed;
				}

			} else {

				if ( event.pageY - this.document.scrollTop() < o.scrollSensitivity ) {
					scrolled = this.document.scrollTop( this.document.scrollTop() - o.scrollSpeed );
				} else if ( this.window.height() - ( event.pageY - this.document.scrollTop() ) <
						o.scrollSensitivity ) {
					scrolled = this.document.scrollTop( this.document.scrollTop() + o.scrollSpeed );
				}

				if ( event.pageX - this.document.scrollLeft() < o.scrollSensitivity ) {
					scrolled = this.document.scrollLeft(
						this.document.scrollLeft() - o.scrollSpeed
					);
				} else if ( this.window.width() - ( event.pageX - this.document.scrollLeft() ) <
						o.scrollSensitivity ) {
					scrolled = this.document.scrollLeft(
						this.document.scrollLeft() + o.scrollSpeed
					);
				}

			}

			if ( scrolled !== false && $.ui.ddmanager && !o.dropBehaviour ) {
				$.ui.ddmanager.prepareOffsets( this, event );
			}
		}

		// Regenerate the absolute position used for position checks
		this.positionAbs = this._convertPositionTo( "absolute" );

		// Set the helper position
		if ( !this.options.axis || this.options.axis !== "y" ) {
			this.helper[ 0 ].style.left = this.position.left + "px";
		}
		if ( !this.options.axis || this.options.axis !== "x" ) {
			this.helper[ 0 ].style.top = this.position.top + "px";
		}

		// Rearrange
		for ( i = this.items.length - 1; i >= 0; i-- ) {

			// Cache variables and intersection, continue if no intersection
			item = this.items[ i ];
			itemElement = item.item[ 0 ];
			intersection = this._intersectsWithPointer( item );
			if ( !intersection ) {
				continue;
			}

			// Only put the placeholder inside the current Container, skip all
			// items from other containers. This works because when moving
			// an item from one container to another the
			// currentContainer is switched before the placeholder is moved.
			//
			// Without this, moving items in "sub-sortables" can cause
			// the placeholder to jitter between the outer and inner container.
			if ( item.instance !== this.currentContainer ) {
				continue;
			}

			// Cannot intersect with itself
			// no useless actions that have been done before
			// no action if the item moved is the parent of the item checked
			if ( itemElement !== this.currentItem[ 0 ] &&
				this.placeholder[ intersection === 1 ? "next" : "prev" ]()[ 0 ] !== itemElement &&
				!$.contains( this.placeholder[ 0 ], itemElement ) &&
				( this.options.type === "semi-dynamic" ?
					!$.contains( this.element[ 0 ], itemElement ) :
					true
				)
			) {

				this.direction = intersection === 1 ? "down" : "up";

				if ( this.options.tolerance === "pointer" || this._intersectsWithSides( item ) ) {
					this._rearrange( event, item );
				} else {
					break;
				}

				this._trigger( "change", event, this._uiHash() );
				break;
			}
		}

		// Post events to containers
		this._contactContainers( event );

		// Interconnect with droppables
		if ( $.ui.ddmanager ) {
			$.ui.ddmanager.drag( this, event );
		}

		// Call callbacks
		this._trigger( "sort", event, this._uiHash() );

		this.lastPositionAbs = this.positionAbs;
		return false;

	},

	_mouseStop: function( event, noPropagation ) {

		if ( !event ) {
			return;
		}

		// If we are using droppables, inform the manager about the drop
		if ( $.ui.ddmanager && !this.options.dropBehaviour ) {
			$.ui.ddmanager.drop( this, event );
		}

		if ( this.options.revert ) {
			var that = this,
				cur = this.placeholder.offset(),
				axis = this.options.axis,
				animation = {};

			if ( !axis || axis === "x" ) {
				animation.left = cur.left - this.offset.parent.left - this.margins.left +
					( this.offsetParent[ 0 ] === this.document[ 0 ].body ?
						0 :
						this.offsetParent[ 0 ].scrollLeft
					);
			}
			if ( !axis || axis === "y" ) {
				animation.top = cur.top - this.offset.parent.top - this.margins.top +
					( this.offsetParent[ 0 ] === this.document[ 0 ].body ?
						0 :
						this.offsetParent[ 0 ].scrollTop
					);
			}
			this.reverting = true;
			$( this.helper ).animate(
				animation,
				parseInt( this.options.revert, 10 ) || 500,
				function() {
					that._clear( event );
				}
			);
		} else {
			this._clear( event, noPropagation );
		}

		return false;

	},

	cancel: function() {

		if ( this.dragging ) {

			this._mouseUp( new $.Event( "mouseup", { target: null } ) );

			if ( this.options.helper === "original" ) {
				this.currentItem.css( this._storedCSS );
				this._removeClass( this.currentItem, "ui-sortable-helper" );
			} else {
				this.currentItem.show();
			}

			// Post deactivating events to containers
			for ( var i = this.containers.length - 1; i >= 0; i-- ) {
				this.containers[ i ]._trigger( "deactivate", null, this._uiHash( this ) );
				if ( this.containers[ i ].containerCache.over ) {
					this.containers[ i ]._trigger( "out", null, this._uiHash( this ) );
					this.containers[ i ].containerCache.over = 0;
				}
			}

		}

		if ( this.placeholder ) {

			// $(this.placeholder[0]).remove(); would have been the jQuery way -
			// unfortunately,
			// it unbinds ALL events from the original node!
			if ( this.placeholder[ 0 ].parentNode ) {
				this.placeholder[ 0 ].parentNode.removeChild( this.placeholder[ 0 ] );
			}
			if ( this.options.helper !== "original" && this.helper &&
					this.helper[ 0 ].parentNode ) {
				this.helper.remove();
			}

			$.extend( this, {
				helper: null,
				dragging: false,
				reverting: false,
				_noFinalSort: null
			} );

			if ( this.domPosition.prev ) {
				$( this.domPosition.prev ).after( this.currentItem );
			} else {
				$( this.domPosition.parent ).prepend( this.currentItem );
			}
		}

		return this;

	},

	serialize: function( o ) {

		var items = this._getItemsAsjQuery( o && o.connected ),
			str = [];
		o = o || {};

		$( items ).each( function() {
			var res = ( $( o.item || this ).attr( o.attribute || "id" ) || "" )
				.match( o.expression || ( /(.+)[\-=_](.+)/ ) );
			if ( res ) {
				str.push(
					( o.key || res[ 1 ] + "[]" ) +
					"=" + ( o.key && o.expression ? res[ 1 ] : res[ 2 ] ) );
			}
		} );

		if ( !str.length && o.key ) {
			str.push( o.key + "=" );
		}

		return str.join( "&" );

	},

	toArray: function( o ) {

		var items = this._getItemsAsjQuery( o && o.connected ),
			ret = [];

		o = o || {};

		items.each( function() {
			ret.push( $( o.item || this ).attr( o.attribute || "id" ) || "" );
		} );
		return ret;

	},

	/* Be careful with the following core functions */
	_intersectsWith: function( item ) {

		var x1 = this.positionAbs.left,
			x2 = x1 + this.helperProportions.width,
			y1 = this.positionAbs.top,
			y2 = y1 + this.helperProportions.height,
			l = item.left,
			r = l + item.width,
			t = item.top,
			b = t + item.height,
			dyClick = this.offset.click.top,
			dxClick = this.offset.click.left,
			isOverElementHeight = ( this.options.axis === "x" ) || ( ( y1 + dyClick ) > t &&
				( y1 + dyClick ) < b ),
			isOverElementWidth = ( this.options.axis === "y" ) || ( ( x1 + dxClick ) > l &&
				( x1 + dxClick ) < r ),
			isOverElement = isOverElementHeight && isOverElementWidth;

		if ( this.options.tolerance === "pointer" ||
			this.options.forcePointerForContainers ||
			( this.options.tolerance !== "pointer" &&
				this.helperProportions[ this.floating ? "width" : "height" ] >
				item[ this.floating ? "width" : "height" ] )
		) {
			return isOverElement;
		} else {

			return ( l < x1 + ( this.helperProportions.width / 2 ) && // Right
																		// Half
				x2 - ( this.helperProportions.width / 2 ) < r && // Left Half
				t < y1 + ( this.helperProportions.height / 2 ) && // Bottom
																	// Half
				y2 - ( this.helperProportions.height / 2 ) < b ); // Top Half

		}
	},

	_intersectsWithPointer: function( item ) {
		var verticalDirection, horizontalDirection,
			isOverElementHeight = ( this.options.axis === "x" ) ||
				this._isOverAxis(
					this.positionAbs.top + this.offset.click.top, item.top, item.height ),
			isOverElementWidth = ( this.options.axis === "y" ) ||
				this._isOverAxis(
					this.positionAbs.left + this.offset.click.left, item.left, item.width ),
			isOverElement = isOverElementHeight && isOverElementWidth;

		if ( !isOverElement ) {
			return false;
		}

		verticalDirection = this._getDragVerticalDirection();
		horizontalDirection = this._getDragHorizontalDirection();

		return this.floating ?
			( ( horizontalDirection === "right" || verticalDirection === "down" ) ? 2 : 1 )
			: ( verticalDirection && ( verticalDirection === "down" ? 2 : 1 ) );

	},

	_intersectsWithSides: function( item ) {

		var isOverBottomHalf = this._isOverAxis( this.positionAbs.top +
				this.offset.click.top, item.top + ( item.height / 2 ), item.height ),
			isOverRightHalf = this._isOverAxis( this.positionAbs.left +
				this.offset.click.left, item.left + ( item.width / 2 ), item.width ),
			verticalDirection = this._getDragVerticalDirection(),
			horizontalDirection = this._getDragHorizontalDirection();

		if ( this.floating && horizontalDirection ) {
			return ( ( horizontalDirection === "right" && isOverRightHalf ) ||
				( horizontalDirection === "left" && !isOverRightHalf ) );
		} else {
			return verticalDirection && ( ( verticalDirection === "down" && isOverBottomHalf ) ||
				( verticalDirection === "up" && !isOverBottomHalf ) );
		}

	},

	_getDragVerticalDirection: function() {
		var delta = this.positionAbs.top - this.lastPositionAbs.top;
		return delta !== 0 && ( delta > 0 ? "down" : "up" );
	},

	_getDragHorizontalDirection: function() {
		var delta = this.positionAbs.left - this.lastPositionAbs.left;
		return delta !== 0 && ( delta > 0 ? "right" : "left" );
	},

	refresh: function( event ) {
		this._refreshItems( event );
		this._setHandleClassName();
		this.refreshPositions();
		return this;
	},

	_connectWith: function() {
		var options = this.options;
		return options.connectWith.constructor === String ?
			[ options.connectWith ] :
			options.connectWith;
	},

	_getItemsAsjQuery: function( connected ) {

		var i, j, cur, inst,
			items = [],
			queries = [],
			connectWith = this._connectWith();

		if ( connectWith && connected ) {
			for ( i = connectWith.length - 1; i >= 0; i-- ) {
				cur = $( connectWith[ i ], this.document[ 0 ] );
				for ( j = cur.length - 1; j >= 0; j-- ) {
					inst = $.data( cur[ j ], this.widgetFullName );
					if ( inst && inst !== this && !inst.options.disabled ) {
						queries.push( [ $.isFunction( inst.options.items ) ?
							inst.options.items.call( inst.element ) :
							$( inst.options.items, inst.element )
								.not( ".ui-sortable-helper" )
								.not( ".ui-sortable-placeholder" ), inst ] );
					}
				}
			}
		}

		queries.push( [ $.isFunction( this.options.items ) ?
			this.options.items
				.call( this.element, null, { options: this.options, item: this.currentItem } ) :
			$( this.options.items, this.element )
				.not( ".ui-sortable-helper" )
				.not( ".ui-sortable-placeholder" ), this ] );

		function addItems() {
			items.push( this );
		}
		for ( i = queries.length - 1; i >= 0; i-- ) {
			queries[ i ][ 0 ].each( addItems );
		}

		return $( items );

	},

	_removeCurrentsFromItems: function() {

		var list = this.currentItem.find( ":data(" + this.widgetName + "-item)" );

		this.items = $.grep( this.items, function( item ) {
			for ( var j = 0; j < list.length; j++ ) {
				if ( list[ j ] === item.item[ 0 ] ) {
					return false;
				}
			}
			return true;
		} );

	},

	_refreshItems: function( event ) {

		this.items = [];
		this.containers = [ this ];

		var i, j, cur, inst, targetData, _queries, item, queriesLength,
			items = this.items,
			queries = [ [ $.isFunction( this.options.items ) ?
				this.options.items.call( this.element[ 0 ], event, { item: this.currentItem } ) :
				$( this.options.items, this.element ), this ] ],
			connectWith = this._connectWith();

		// Shouldn't be run the first time through due to massive slow-down
		if ( connectWith && this.ready ) {
			for ( i = connectWith.length - 1; i >= 0; i-- ) {
				cur = $( connectWith[ i ], this.document[ 0 ] );
				for ( j = cur.length - 1; j >= 0; j-- ) {
					inst = $.data( cur[ j ], this.widgetFullName );
					if ( inst && inst !== this && !inst.options.disabled ) {
						queries.push( [ $.isFunction( inst.options.items ) ?
							inst.options.items
								.call( inst.element[ 0 ], event, { item: this.currentItem } ) :
							$( inst.options.items, inst.element ), inst ] );
						this.containers.push( inst );
					}
				}
			}
		}

		for ( i = queries.length - 1; i >= 0; i-- ) {
			targetData = queries[ i ][ 1 ];
			_queries = queries[ i ][ 0 ];

			for ( j = 0, queriesLength = _queries.length; j < queriesLength; j++ ) {
				item = $( _queries[ j ] );

				// Data for target checking (mouse manager)
				item.data( this.widgetName + "-item", targetData );

				items.push( {
					item: item,
					instance: targetData,
					width: 0, height: 0,
					left: 0, top: 0
				} );
			}
		}

	},

	refreshPositions: function( fast ) {

		// Determine whether items are being displayed horizontally
		this.floating = this.items.length ?
			this.options.axis === "x" || this._isFloating( this.items[ 0 ].item ) :
			false;

		// This has to be redone because due to the item being moved out/into
		// the offsetParent,
		// the offsetParent's position will change
		if ( this.offsetParent && this.helper ) {
			this.offset.parent = this._getParentOffset();
		}

		var i, item, t, p;

		for ( i = this.items.length - 1; i >= 0; i-- ) {
			item = this.items[ i ];

			// We ignore calculating positions of all connected containers when
			// we're not over them
			if ( item.instance !== this.currentContainer && this.currentContainer &&
					item.item[ 0 ] !== this.currentItem[ 0 ] ) {
				continue;
			}

			t = this.options.toleranceElement ?
				$( this.options.toleranceElement, item.item ) :
				item.item;

			if ( !fast ) {
				item.width = t.outerWidth();
				item.height = t.outerHeight();
			}

			p = t.offset();
			item.left = p.left;
			item.top = p.top;
		}

		if ( this.options.custom && this.options.custom.refreshContainers ) {
			this.options.custom.refreshContainers.call( this );
		} else {
			for ( i = this.containers.length - 1; i >= 0; i-- ) {
				p = this.containers[ i ].element.offset();
				this.containers[ i ].containerCache.left = p.left;
				this.containers[ i ].containerCache.top = p.top;
				this.containers[ i ].containerCache.width =
					this.containers[ i ].element.outerWidth();
				this.containers[ i ].containerCache.height =
					this.containers[ i ].element.outerHeight();
			}
		}

		return this;
	},

	_createPlaceholder: function( that ) {
		that = that || this;
		var className,
			o = that.options;

		if ( !o.placeholder || o.placeholder.constructor === String ) {
			className = o.placeholder;
			o.placeholder = {
				element: function() {

					var nodeName = that.currentItem[ 0 ].nodeName.toLowerCase(),
						element = $( "<" + nodeName + ">", that.document[ 0 ] );

						that._addClass( element, "ui-sortable-placeholder",
								className || that.currentItem[ 0 ].className )
							._removeClass( element, "ui-sortable-helper" );

					if ( nodeName === "tbody" ) {
						that._createTrPlaceholder(
							that.currentItem.find( "tr" ).eq( 0 ),
							$( "<tr>", that.document[ 0 ] ).appendTo( element )
						);
					} else if ( nodeName === "tr" ) {
						that._createTrPlaceholder( that.currentItem, element );
					} else if ( nodeName === "img" ) {
						element.attr( "src", that.currentItem.attr( "src" ) );
					}

					if ( !className ) {
						element.css( "visibility", "hidden" );
					}

					return element;
				},
				update: function( container, p ) {

					// 1. If a className is set as 'placeholder option, we don't
					// force sizes -
					// the class is responsible for that
					// 2. The option 'forcePlaceholderSize can be enabled to
					// force it even if a
					// class name is specified
					if ( className && !o.forcePlaceholderSize ) {
						return;
					}

					// If the element doesn't have a actual height by itself
					// (without styles coming
					// from a stylesheet), it receives the inline height from
					// the dragged item
					if ( !p.height() ) {
						p.height(
							that.currentItem.innerHeight() -
							parseInt( that.currentItem.css( "paddingTop" ) || 0, 10 ) -
							parseInt( that.currentItem.css( "paddingBottom" ) || 0, 10 ) );
					}
					if ( !p.width() ) {
						p.width(
							that.currentItem.innerWidth() -
							parseInt( that.currentItem.css( "paddingLeft" ) || 0, 10 ) -
							parseInt( that.currentItem.css( "paddingRight" ) || 0, 10 ) );
					}
				}
			};
		}

		// Create the placeholder
		that.placeholder = $( o.placeholder.element.call( that.element, that.currentItem ) );

		// Append it after the actual current item
		that.currentItem.after( that.placeholder );

		// Update the size of the placeholder (TODO: Logic to fuzzy, see line
		// 316/317)
		o.placeholder.update( that, that.placeholder );

	},

	_createTrPlaceholder: function( sourceTr, targetTr ) {
		var that = this;

		sourceTr.children().each( function() {
			$( "<td>&#160;</td>", that.document[ 0 ] )
				.attr( "colspan", $( this ).attr( "colspan" ) || 1 )
				.appendTo( targetTr );
		} );
	},

	_contactContainers: function( event ) {
		var i, j, dist, itemWithLeastDistance, posProperty, sizeProperty, cur, nearBottom,
			floating, axis,
			innermostContainer = null,
			innermostIndex = null;

		// Get innermost container that intersects with item
		for ( i = this.containers.length - 1; i >= 0; i-- ) {

			// Never consider a container that's located within the item itself
			if ( $.contains( this.currentItem[ 0 ], this.containers[ i ].element[ 0 ] ) ) {
				continue;
			}

			if ( this._intersectsWith( this.containers[ i ].containerCache ) ) {

				// If we've already found a container and it's more "inner" than
				// this, then continue
				if ( innermostContainer &&
						$.contains(
							this.containers[ i ].element[ 0 ],
							innermostContainer.element[ 0 ] ) ) {
					continue;
				}

				innermostContainer = this.containers[ i ];
				innermostIndex = i;

			} else {

				// container doesn't intersect. trigger "out" event if necessary
				if ( this.containers[ i ].containerCache.over ) {
					this.containers[ i ]._trigger( "out", event, this._uiHash( this ) );
					this.containers[ i ].containerCache.over = 0;
				}
			}

		}

		// If no intersecting containers found, return
		if ( !innermostContainer ) {
			return;
		}

		// Move the item into the container if it's not there already
		if ( this.containers.length === 1 ) {
			if ( !this.containers[ innermostIndex ].containerCache.over ) {
				this.containers[ innermostIndex ]._trigger( "over", event, this._uiHash( this ) );
				this.containers[ innermostIndex ].containerCache.over = 1;
			}
		} else {

			// When entering a new container, we will find the item with the
			// least distance and
			// append our item near it
			dist = 10000;
			itemWithLeastDistance = null;
			floating = innermostContainer.floating || this._isFloating( this.currentItem );
			posProperty = floating ? "left" : "top";
			sizeProperty = floating ? "width" : "height";
			axis = floating ? "pageX" : "pageY";

			for ( j = this.items.length - 1; j >= 0; j-- ) {
				if ( !$.contains(
						this.containers[ innermostIndex ].element[ 0 ], this.items[ j ].item[ 0 ] )
				) {
					continue;
				}
				if ( this.items[ j ].item[ 0 ] === this.currentItem[ 0 ] ) {
					continue;
				}

				cur = this.items[ j ].item.offset()[ posProperty ];
				nearBottom = false;
				if ( event[ axis ] - cur > this.items[ j ][ sizeProperty ] / 2 ) {
					nearBottom = true;
				}

				if ( Math.abs( event[ axis ] - cur ) < dist ) {
					dist = Math.abs( event[ axis ] - cur );
					itemWithLeastDistance = this.items[ j ];
					this.direction = nearBottom ? "up" : "down";
				}
			}

			// Check if dropOnEmpty is enabled
			if ( !itemWithLeastDistance && !this.options.dropOnEmpty ) {
				return;
			}

			if ( this.currentContainer === this.containers[ innermostIndex ] ) {
				if ( !this.currentContainer.containerCache.over ) {
					this.containers[ innermostIndex ]._trigger( "over", event, this._uiHash() );
					this.currentContainer.containerCache.over = 1;
				}
				return;
			}

			itemWithLeastDistance ?
				this._rearrange( event, itemWithLeastDistance, null, true ) :
				this._rearrange( event, null, this.containers[ innermostIndex ].element, true );
			this._trigger( "change", event, this._uiHash() );
			this.containers[ innermostIndex ]._trigger( "change", event, this._uiHash( this ) );
			this.currentContainer = this.containers[ innermostIndex ];

			// Update the placeholder
			this.options.placeholder.update( this.currentContainer, this.placeholder );

			this.containers[ innermostIndex ]._trigger( "over", event, this._uiHash( this ) );
			this.containers[ innermostIndex ].containerCache.over = 1;
		}

	},

	_createHelper: function( event ) {

		var o = this.options,
			helper = $.isFunction( o.helper ) ?
				$( o.helper.apply( this.element[ 0 ], [ event, this.currentItem ] ) ) :
				( o.helper === "clone" ? this.currentItem.clone() : this.currentItem );

		// Add the helper to the DOM if that didn't happen already
		if ( !helper.parents( "body" ).length ) {
			$( o.appendTo !== "parent" ?
				o.appendTo :
				this.currentItem[ 0 ].parentNode )[ 0 ].appendChild( helper[ 0 ] );
		}

		if ( helper[ 0 ] === this.currentItem[ 0 ] ) {
			this._storedCSS = {
				width: this.currentItem[ 0 ].style.width,
				height: this.currentItem[ 0 ].style.height,
				position: this.currentItem.css( "position" ),
				top: this.currentItem.css( "top" ),
				left: this.currentItem.css( "left" )
			};
		}

		if ( !helper[ 0 ].style.width || o.forceHelperSize ) {
			helper.width( this.currentItem.width() );
		}
		if ( !helper[ 0 ].style.height || o.forceHelperSize ) {
			helper.height( this.currentItem.height() );
		}

		return helper;

	},

	_adjustOffsetFromHelper: function( obj ) {
		if ( typeof obj === "string" ) {
			obj = obj.split( " " );
		}
		if ( $.isArray( obj ) ) {
			obj = { left: +obj[ 0 ], top: +obj[ 1 ] || 0 };
		}
		if ( "left" in obj ) {
			this.offset.click.left = obj.left + this.margins.left;
		}
		if ( "right" in obj ) {
			this.offset.click.left = this.helperProportions.width - obj.right + this.margins.left;
		}
		if ( "top" in obj ) {
			this.offset.click.top = obj.top + this.margins.top;
		}
		if ( "bottom" in obj ) {
			this.offset.click.top = this.helperProportions.height - obj.bottom + this.margins.top;
		}
	},

	_getParentOffset: function() {

		// Get the offsetParent and cache its position
		this.offsetParent = this.helper.offsetParent();
		var po = this.offsetParent.offset();

		// This is a special case where we need to modify a offset calculated on
		// start, since the
		// following happened:
		// 1. The position of the helper is absolute, so it's position is
		// calculated based on the
		// next positioned parent
		// 2. The actual offset parent is a child of the scroll parent, and the
		// scroll parent isn't
		// the document, which means that the scroll is included in the initial
		// calculation of the
		// offset of the parent, and never recalculated upon drag
		if ( this.cssPosition === "absolute" && this.scrollParent[ 0 ] !== this.document[ 0 ] &&
				$.contains( this.scrollParent[ 0 ], this.offsetParent[ 0 ] ) ) {
			po.left += this.scrollParent.scrollLeft();
			po.top += this.scrollParent.scrollTop();
		}

		// This needs to be actually done for all browsers, since pageX/pageY
		// includes this
		// information with an ugly IE fix
		if ( this.offsetParent[ 0 ] === this.document[ 0 ].body ||
				( this.offsetParent[ 0 ].tagName &&
				this.offsetParent[ 0 ].tagName.toLowerCase() === "html" && $.ui.ie ) ) {
			po = { top: 0, left: 0 };
		}

		return {
			top: po.top + ( parseInt( this.offsetParent.css( "borderTopWidth" ), 10 ) || 0 ),
			left: po.left + ( parseInt( this.offsetParent.css( "borderLeftWidth" ), 10 ) || 0 )
		};

	},

	_getRelativeOffset: function() {

		if ( this.cssPosition === "relative" ) {
			var p = this.currentItem.position();
			return {
				top: p.top - ( parseInt( this.helper.css( "top" ), 10 ) || 0 ) +
					this.scrollParent.scrollTop(),
				left: p.left - ( parseInt( this.helper.css( "left" ), 10 ) || 0 ) +
					this.scrollParent.scrollLeft()
			};
		} else {
			return { top: 0, left: 0 };
		}

	},

	_cacheMargins: function() {
		this.margins = {
			left: ( parseInt( this.currentItem.css( "marginLeft" ), 10 ) || 0 ),
			top: ( parseInt( this.currentItem.css( "marginTop" ), 10 ) || 0 )
		};
	},

	_cacheHelperProportions: function() {
		this.helperProportions = {
			width: this.helper.outerWidth(),
			height: this.helper.outerHeight()
		};
	},

	_setContainment: function() {

		var ce, co, over,
			o = this.options;
		if ( o.containment === "parent" ) {
			o.containment = this.helper[ 0 ].parentNode;
		}
		if ( o.containment === "document" || o.containment === "window" ) {
			this.containment = [
				0 - this.offset.relative.left - this.offset.parent.left,
				0 - this.offset.relative.top - this.offset.parent.top,
				o.containment === "document" ?
					this.document.width() :
					this.window.width() - this.helperProportions.width - this.margins.left,
				( o.containment === "document" ?
					( this.document.height() || document.body.parentNode.scrollHeight ) :
					this.window.height() || this.document[ 0 ].body.parentNode.scrollHeight
				) - this.helperProportions.height - this.margins.top
			];
		}

		if ( !( /^(document|window|parent)$/ ).test( o.containment ) ) {
			ce = $( o.containment )[ 0 ];
			co = $( o.containment ).offset();
			over = ( $( ce ).css( "overflow" ) !== "hidden" );

			this.containment = [
				co.left + ( parseInt( $( ce ).css( "borderLeftWidth" ), 10 ) || 0 ) +
					( parseInt( $( ce ).css( "paddingLeft" ), 10 ) || 0 ) - this.margins.left,
				co.top + ( parseInt( $( ce ).css( "borderTopWidth" ), 10 ) || 0 ) +
					( parseInt( $( ce ).css( "paddingTop" ), 10 ) || 0 ) - this.margins.top,
				co.left + ( over ? Math.max( ce.scrollWidth, ce.offsetWidth ) : ce.offsetWidth ) -
					( parseInt( $( ce ).css( "borderLeftWidth" ), 10 ) || 0 ) -
					( parseInt( $( ce ).css( "paddingRight" ), 10 ) || 0 ) -
					this.helperProportions.width - this.margins.left,
				co.top + ( over ? Math.max( ce.scrollHeight, ce.offsetHeight ) : ce.offsetHeight ) -
					( parseInt( $( ce ).css( "borderTopWidth" ), 10 ) || 0 ) -
					( parseInt( $( ce ).css( "paddingBottom" ), 10 ) || 0 ) -
					this.helperProportions.height - this.margins.top
			];
		}

	},

	_convertPositionTo: function( d, pos ) {

		if ( !pos ) {
			pos = this.position;
		}
		var mod = d === "absolute" ? 1 : -1,
			scroll = this.cssPosition === "absolute" &&
				!( this.scrollParent[ 0 ] !== this.document[ 0 ] &&
				$.contains( this.scrollParent[ 0 ], this.offsetParent[ 0 ] ) ) ?
					this.offsetParent :
					this.scrollParent,
			scrollIsRootNode = ( /(html|body)/i ).test( scroll[ 0 ].tagName );

		return {
			top: (

				// The absolute mouse position
				pos.top	+

				// Only for relative positioned nodes: Relative offset from
				// element to offset parent
				this.offset.relative.top * mod +

				// The offsetParent's offset without borders (offset + border)
				this.offset.parent.top * mod -
				( ( this.cssPosition === "fixed" ?
					-this.scrollParent.scrollTop() :
					( scrollIsRootNode ? 0 : scroll.scrollTop() ) ) * mod )
			),
			left: (

				// The absolute mouse position
				pos.left +

				// Only for relative positioned nodes: Relative offset from
				// element to offset parent
				this.offset.relative.left * mod +

				// The offsetParent's offset without borders (offset + border)
				this.offset.parent.left * mod	-
				( ( this.cssPosition === "fixed" ?
					-this.scrollParent.scrollLeft() : scrollIsRootNode ? 0 :
					scroll.scrollLeft() ) * mod )
			)
		};

	},

	_generatePosition: function( event ) {

		var top, left,
			o = this.options,
			pageX = event.pageX,
			pageY = event.pageY,
			scroll = this.cssPosition === "absolute" &&
				!( this.scrollParent[ 0 ] !== this.document[ 0 ] &&
				$.contains( this.scrollParent[ 0 ], this.offsetParent[ 0 ] ) ) ?
					this.offsetParent :
					this.scrollParent,
				scrollIsRootNode = ( /(html|body)/i ).test( scroll[ 0 ].tagName );

		// This is another very weird special case that only happens for
		// relative elements:
		// 1. If the css position is relative
		// 2. and the scroll parent is the document or similar to the offset
		// parent
		// we have to refresh the relative offset during the scroll so there are
		// no jumps
		if ( this.cssPosition === "relative" && !( this.scrollParent[ 0 ] !== this.document[ 0 ] &&
				this.scrollParent[ 0 ] !== this.offsetParent[ 0 ] ) ) {
			this.offset.relative = this._getRelativeOffset();
		}

		/*
		 * - Position constraining - Constrain the position to a mix of grid,
		 * containment.
		 */

		if ( this.originalPosition ) { // If we are not dragging yet, we won't
										// check for options

			if ( this.containment ) {
				if ( event.pageX - this.offset.click.left < this.containment[ 0 ] ) {
					pageX = this.containment[ 0 ] + this.offset.click.left;
				}
				if ( event.pageY - this.offset.click.top < this.containment[ 1 ] ) {
					pageY = this.containment[ 1 ] + this.offset.click.top;
				}
				if ( event.pageX - this.offset.click.left > this.containment[ 2 ] ) {
					pageX = this.containment[ 2 ] + this.offset.click.left;
				}
				if ( event.pageY - this.offset.click.top > this.containment[ 3 ] ) {
					pageY = this.containment[ 3 ] + this.offset.click.top;
				}
			}

			if ( o.grid ) {
				top = this.originalPageY + Math.round( ( pageY - this.originalPageY ) /
					o.grid[ 1 ] ) * o.grid[ 1 ];
				pageY = this.containment ?
					( ( top - this.offset.click.top >= this.containment[ 1 ] &&
						top - this.offset.click.top <= this.containment[ 3 ] ) ?
							top :
							( ( top - this.offset.click.top >= this.containment[ 1 ] ) ?
								top - o.grid[ 1 ] : top + o.grid[ 1 ] ) ) :
								top;

				left = this.originalPageX + Math.round( ( pageX - this.originalPageX ) /
					o.grid[ 0 ] ) * o.grid[ 0 ];
				pageX = this.containment ?
					( ( left - this.offset.click.left >= this.containment[ 0 ] &&
						left - this.offset.click.left <= this.containment[ 2 ] ) ?
							left :
							( ( left - this.offset.click.left >= this.containment[ 0 ] ) ?
								left - o.grid[ 0 ] : left + o.grid[ 0 ] ) ) :
								left;
			}

		}

		return {
			top: (

				// The absolute mouse position
				pageY -

				// Click offset (relative to the element)
				this.offset.click.top -

				// Only for relative positioned nodes: Relative offset from
				// element to offset parent
				this.offset.relative.top -

				// The offsetParent's offset without borders (offset + border)
				this.offset.parent.top +
				( ( this.cssPosition === "fixed" ?
					-this.scrollParent.scrollTop() :
					( scrollIsRootNode ? 0 : scroll.scrollTop() ) ) )
			),
			left: (

				// The absolute mouse position
				pageX -

				// Click offset (relative to the element)
				this.offset.click.left -

				// Only for relative positioned nodes: Relative offset from
				// element to offset parent
				this.offset.relative.left -

				// The offsetParent's offset without borders (offset + border)
				this.offset.parent.left +
				( ( this.cssPosition === "fixed" ?
					-this.scrollParent.scrollLeft() :
					scrollIsRootNode ? 0 : scroll.scrollLeft() ) )
			)
		};

	},

	_rearrange: function( event, i, a, hardRefresh ) {

		a ? a[ 0 ].appendChild( this.placeholder[ 0 ] ) :
			i.item[ 0 ].parentNode.insertBefore( this.placeholder[ 0 ],
				( this.direction === "down" ? i.item[ 0 ] : i.item[ 0 ].nextSibling ) );

		// Various things done here to improve the performance:
		// 1. we create a setTimeout, that calls refreshPositions
		// 2. on the instance, we have a counter variable, that get's higher
		// after every append
		// 3. on the local scope, we copy the counter variable, and check in the
		// timeout,
		// if it's still the same
		// 4. this lets only the last addition to the timeout stack through
		this.counter = this.counter ? ++this.counter : 1;
		var counter = this.counter;

		this._delay( function() {
			if ( counter === this.counter ) {

				// Precompute after each DOM insertion, NOT on mousemove
				this.refreshPositions( !hardRefresh );
			}
		} );

	},

	_clear: function( event, noPropagation ) {

		this.reverting = false;

		// We delay all events that have to be triggered to after the point
		// where the placeholder
		// has been removed and everything else normalized again
		var i,
			delayedTriggers = [];

		// We first have to update the dom position of the actual currentItem
		// Note: don't do it if the current item is already removed (by a user),
		// or it gets
		// reappended (see #4088)
		if ( !this._noFinalSort && this.currentItem.parent().length ) {
			this.placeholder.before( this.currentItem );
		}
		this._noFinalSort = null;

		if ( this.helper[ 0 ] === this.currentItem[ 0 ] ) {
			for ( i in this._storedCSS ) {
				if ( this._storedCSS[ i ] === "auto" || this._storedCSS[ i ] === "static" ) {
					this._storedCSS[ i ] = "";
				}
			}
			this.currentItem.css( this._storedCSS );
			this._removeClass( this.currentItem, "ui-sortable-helper" );
		} else {
			this.currentItem.show();
		}

		if ( this.fromOutside && !noPropagation ) {
			delayedTriggers.push( function( event ) {
				this._trigger( "receive", event, this._uiHash( this.fromOutside ) );
			} );
		}
		if ( ( this.fromOutside ||
				this.domPosition.prev !==
				this.currentItem.prev().not( ".ui-sortable-helper" )[ 0 ] ||
				this.domPosition.parent !== this.currentItem.parent()[ 0 ] ) && !noPropagation ) {

			// Trigger update callback if the DOM position has changed
			delayedTriggers.push( function( event ) {
				this._trigger( "update", event, this._uiHash() );
			} );
		}

		// Check if the items Container has Changed and trigger appropriate
		// events.
		if ( this !== this.currentContainer ) {
			if ( !noPropagation ) {
				delayedTriggers.push( function( event ) {
					this._trigger( "remove", event, this._uiHash() );
				} );
				delayedTriggers.push( ( function( c ) {
					return function( event ) {
						c._trigger( "receive", event, this._uiHash( this ) );
					};
				} ).call( this, this.currentContainer ) );
				delayedTriggers.push( ( function( c ) {
					return function( event ) {
						c._trigger( "update", event, this._uiHash( this ) );
					};
				} ).call( this, this.currentContainer ) );
			}
		}

		// Post events to containers
		function delayEvent( type, instance, container ) {
			return function( event ) {
				container._trigger( type, event, instance._uiHash( instance ) );
			};
		}
		for ( i = this.containers.length - 1; i >= 0; i-- ) {
			if ( !noPropagation ) {
				delayedTriggers.push( delayEvent( "deactivate", this, this.containers[ i ] ) );
			}
			if ( this.containers[ i ].containerCache.over ) {
				delayedTriggers.push( delayEvent( "out", this, this.containers[ i ] ) );
				this.containers[ i ].containerCache.over = 0;
			}
		}

		// Do what was originally in plugins
		if ( this.storedCursor ) {
			this.document.find( "body" ).css( "cursor", this.storedCursor );
			this.storedStylesheet.remove();
		}
		if ( this._storedOpacity ) {
			this.helper.css( "opacity", this._storedOpacity );
		}
		if ( this._storedZIndex ) {
			this.helper.css( "zIndex", this._storedZIndex === "auto" ? "" : this._storedZIndex );
		}

		this.dragging = false;

		if ( !noPropagation ) {
			this._trigger( "beforeStop", event, this._uiHash() );
		}

		// $(this.placeholder[0]).remove(); would have been the jQuery way -
		// unfortunately,
		// it unbinds ALL events from the original node!
		this.placeholder[ 0 ].parentNode.removeChild( this.placeholder[ 0 ] );

		if ( !this.cancelHelperRemoval ) {
			if ( this.helper[ 0 ] !== this.currentItem[ 0 ] ) {
				this.helper.remove();
			}
			this.helper = null;
		}

		if ( !noPropagation ) {
			for ( i = 0; i < delayedTriggers.length; i++ ) {

				// Trigger all delayed events
				delayedTriggers[ i ].call( this, event );
			}
			this._trigger( "stop", event, this._uiHash() );
		}

		this.fromOutside = false;
		return !this.cancelHelperRemoval;

	},

	_trigger: function() {
		if ( $.Widget.prototype._trigger.apply( this, arguments ) === false ) {
			this.cancel();
		}
	},

	_uiHash: function( _inst ) {
		var inst = _inst || this;
		return {
			helper: inst.helper,
			placeholder: inst.placeholder || $( [] ),
			position: inst.position,
			originalPosition: inst.originalPosition,
			offset: inst.positionAbs,
			item: inst.currentItem,
			sender: _inst ? _inst.element : null
		};
	}

} );


// jscs:disable maximumLineLength
/* jscs:disable requireCamelCaseOrUpperCaseIdentifiers */
/*
 * ! jQuery UI Datepicker 1.12.1 http://jqueryui.com
 * 
 * Copyright jQuery Foundation and other contributors Released under the MIT
 * license. http://jquery.org/license
 */

// >>label: Datepicker
// >>group: Widgets
// >>description: Displays a calendar from an input or inline for selecting
// dates.
// >>docs: http://api.jqueryui.com/datepicker/
// >>demos: http://jqueryui.com/datepicker/
// >>css.structure: ../../themes/base/core.css
// >>css.structure: ../../themes/base/datepicker.css
// >>css.theme: ../../themes/base/theme.css



$.extend( $.ui, { datepicker: { version: "1.12.1" } } );

var datepicker_instActive;

function datepicker_getZindex( elem ) {
	var position, value;
	while ( elem.length && elem[ 0 ] !== document ) {

		// Ignore z-index if position is set to a value where z-index is ignored
		// by the browser
		// This makes behavior of this function consistent across browsers
		// WebKit always returns auto if the element is positioned
		position = elem.css( "position" );
		if ( position === "absolute" || position === "relative" || position === "fixed" ) {

			// IE returns 0 when zIndex is not specified
			// other browsers return a string
			// we ignore the case of nested elements with an explicit value of 0
			// <div style="z-index: -10;"><div style="z-index: 0;"></div></div>
			value = parseInt( elem.css( "zIndex" ), 10 );
			if ( !isNaN( value ) && value !== 0 ) {
				return value;
			}
		}
		elem = elem.parent();
	}

	return 0;
}
/*
 * Date picker manager. Use the singleton instance of this class, $.datepicker,
 * to interact with the date picker. Settings for (groups of) date pickers are
 * maintained in an instance object, allowing multiple different settings on the
 * same page.
 */

function Datepicker() {
	this._curInst = null; // The current instance in use
	this._keyEvent = false; // If the last event was a key event
	this._disabledInputs = []; // List of date picker inputs that have been
								// disabled
	this._datepickerShowing = false; // True if the popup picker is showing ,
										// false if not
	this._inDialog = false; // True if showing within a "dialog", false if not
	this._mainDivId = "ui-datepicker-div"; // The ID of the main datepicker
											// division
	this._inlineClass = "ui-datepicker-inline"; // The name of the inline marker
												// class
	this._appendClass = "ui-datepicker-append"; // The name of the append marker
												// class
	this._triggerClass = "ui-datepicker-trigger"; // The name of the trigger
													// marker class
	this._dialogClass = "ui-datepicker-dialog"; // The name of the dialog marker
												// class
	this._disableClass = "ui-datepicker-disabled"; // The name of the disabled
													// covering marker class
	this._unselectableClass = "ui-datepicker-unselectable"; // The name of the
															// unselectable cell
															// marker class
	this._currentClass = "ui-datepicker-current-day"; // The name of the
														// current day marker
														// class
	this._dayOverClass = "ui-datepicker-days-cell-over"; // The name of the
															// day hover marker
															// class
	this.regional = []; // Available regional settings, indexed by language code
	this.regional[ "" ] = { // Default regional settings
		closeText: "Done", // Display text for close link
		prevText: "Prev", // Display text for previous month link
		nextText: "Next", // Display text for next month link
		currentText: "Today", // Display text for current month link
		monthNames: [ "January","February","March","April","May","June",
			"July","August","September","October","November","December" ], // Names
																			// of
																			// months
																			// for
																			// drop-down
																			// and
																			// formatting
		monthNamesShort: [ "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" ], // For
																													// formatting
		dayNames: [ "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday" ], // For
																									// formatting
		dayNamesShort: [ "Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat" ], // For
																			// formatting
		dayNamesMin: [ "Su","Mo","Tu","We","Th","Fr","Sa" ], // Column
																// headings for
																// days starting
																// at Sunday
		weekHeader: "Wk", // Column header for week of the year
		dateFormat: "mm/dd/yy", // See format options on parseDate
		firstDay: 0, // The first day of the week, Sun = 0, Mon = 1, ...
		isRTL: false, // True if right-to-left language, false if
						// left-to-right
		showMonthAfterYear: false, // True if the year select precedes month,
									// false for month then year
		yearSuffix: "" // Additional text to append to the year in the month
						// headers
	};
	this._defaults = { // Global defaults for all the date picker instances
		showOn: "focus", // "focus" for popup on focus,
			// "button" for trigger button, or "both" for either
		showAnim: "fadeIn", // Name of jQuery animation for popup
		showOptions: {}, // Options for enhanced animations
		defaultDate: null, // Used when field is blank: actual date,
			// +/-number for offset from today, null for today
		appendText: "", // Display text following the input box, e.g. showing
						// the format
		buttonText: "...", // Text for trigger button
		buttonImage: "", // URL for trigger button image
		buttonImageOnly: false, // True if the image appears alone, false if it
								// appears on a button
		hideIfNoPrevNext: false, // True to hide next/previous month links
			// if not applicable, false to just disable them
		navigationAsDateFormat: false, // True if date formatting applied to
										// prev/today/next links
		gotoCurrent: false, // True if today link goes back to current selection
							// instead
		changeMonth: false, // True if month can be selected directly, false if
							// only prev/next
		changeYear: false, // True if year can be selected directly, false if
							// only prev/next
		yearRange: "c-10:c+10", // Range of years to display in drop-down,
			// either relative to today's year (-nn:+nn), relative to currently
			// displayed year
			// (c-nn:c+nn), absolute (nnnn:nnnn), or a combination of the above
			// (nnnn:-n)
		showOtherMonths: false, // True to show dates in other months, false to
								// leave blank
		selectOtherMonths: false, // True to allow selection of dates in other
									// months, false for unselectable
		showWeek: false, // True to show week of the year, false to not show
							// it
		calculateWeek: this.iso8601Week, // How to calculate the week of the
											// year,
			// takes a Date and returns the number of the week for it
		shortYearCutoff: "+10", // Short year values < this are in the current
								// century,
			// > this are in the previous century,
			// string value starting with "+" for current year + value
		minDate: null, // The earliest selectable date, or null for no limit
		maxDate: null, // The latest selectable date, or null for no limit
		duration: "fast", // Duration of display/closure
		beforeShowDay: null, // Function that takes a date and returns an
								// array with
			// [0] = true if selectable, false if not, [1] = custom CSS class
			// name(s) or "",
			// [2] = cell title (optional), e.g. $.datepicker.noWeekends
		beforeShow: null, // Function that takes an input field and
			// returns a set of custom settings for the date picker
		onSelect: null, // Define a callback function when a date is selected
		onChangeMonthYear: null, // Define a callback function when the month
									// or year is changed
		onClose: null, // Define a callback function when the datepicker is
						// closed
		numberOfMonths: 1, // Number of months to show at a time
		showCurrentAtPos: 0, // The position in multipe months at which to
								// show the current month (starting at 0)
		stepMonths: 1, // Number of months to step back/forward
		stepBigMonths: 12, // Number of months to step back/forward for the big
							// links
		altField: "", // Selector for an alternate field to store selected
						// dates into
		altFormat: "", // The date format to use for the alternate field
		constrainInput: true, // The input is constrained by the current date
								// format
		showButtonPanel: false, // True to show button panel, false to not show
								// it
		autoSize: false, // True to size the input for the date format, false
							// to leave as is
		disabled: false // The initial disabled state
	};
	$.extend( this._defaults, this.regional[ "" ] );
	this.regional.en = $.extend( true, {}, this.regional[ "" ] );
	this.regional[ "en-US" ] = $.extend( true, {}, this.regional.en );
	this.dpDiv = datepicker_bindHover( $( "<div id='" + this._mainDivId + "' class='ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all'></div>" ) );
}

$.extend( Datepicker.prototype, {
	/*
	 * Class name added to elements to indicate already configured with a date
	 * picker.
	 */
	markerClassName: "hasDatepicker",

	// Keep track of the maximum number of rows displayed (see #7043)
	maxRows: 4,

	// TODO rename to "widget" when switching to widget factory
	_widgetDatepicker: function() {
		return this.dpDiv;
	},

	/*
	 * Override the default settings for all instances of the date picker.
	 * @param settings object - the new settings to use as defaults (anonymous
	 * object) @return the manager object
	 */
	setDefaults: function( settings ) {
		datepicker_extendRemove( this._defaults, settings || {} );
		return this;
	},

	/*
	 * Attach the date picker to a jQuery selection. @param target element - the
	 * target input field or division or span @param settings object - the new
	 * settings to use for this date picker instance (anonymous)
	 */
	_attachDatepicker: function( target, settings ) {
		var nodeName, inline, inst;
		nodeName = target.nodeName.toLowerCase();
		inline = ( nodeName === "div" || nodeName === "span" );
		if ( !target.id ) {
			this.uuid += 1;
			target.id = "dp" + this.uuid;
		}
		inst = this._newInst( $( target ), inline );
		inst.settings = $.extend( {}, settings || {} );
		if ( nodeName === "input" ) {
			this._connectDatepicker( target, inst );
		} else if ( inline ) {
			this._inlineDatepicker( target, inst );
		}
	},

	/* Create a new instance object. */
	_newInst: function( target, inline ) {
		var id = target[ 0 ].id.replace( /([^A-Za-z0-9_\-])/g, "\\\\$1" ); // escape
																			// jQuery
																			// meta
																			// chars
		return { id: id, input: target, // associated target
			selectedDay: 0, selectedMonth: 0, selectedYear: 0, // current
																// selection
			drawMonth: 0, drawYear: 0, // month being drawn
			inline: inline, // is datepicker inline or not
			dpDiv: ( !inline ? this.dpDiv : // presentation div
			datepicker_bindHover( $( "<div class='" + this._inlineClass + " ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all'></div>" ) ) ) };
	},

	/* Attach the date picker to an input field. */
	_connectDatepicker: function( target, inst ) {
		var input = $( target );
		inst.append = $( [] );
		inst.trigger = $( [] );
		if ( input.hasClass( this.markerClassName ) ) {
			return;
		}
		this._attachments( input, inst );
		input.addClass( this.markerClassName ).on( "keydown", this._doKeyDown ).
			on( "keypress", this._doKeyPress ).on( "keyup", this._doKeyUp );
		this._autoSize( inst );
		$.data( target, "datepicker", inst );

		// If disabled option is true, disable the datepicker once it has been
		// attached to the input (see ticket #5665)
		if ( inst.settings.disabled ) {
			this._disableDatepicker( target );
		}
	},

	/* Make attachments based on settings. */
	_attachments: function( input, inst ) {
		var showOn, buttonText, buttonImage,
			appendText = this._get( inst, "appendText" ),
			isRTL = this._get( inst, "isRTL" );

		if ( inst.append ) {
			inst.append.remove();
		}
		if ( appendText ) {
			inst.append = $( "<span class='" + this._appendClass + "'>" + appendText + "</span>" );
			input[ isRTL ? "before" : "after" ]( inst.append );
		}

		input.off( "focus", this._showDatepicker );

		if ( inst.trigger ) {
			inst.trigger.remove();
		}

		showOn = this._get( inst, "showOn" );
		if ( showOn === "focus" || showOn === "both" ) { // pop-up date
															// picker when in
															// the marked field
			input.on( "focus", this._showDatepicker );
		}
		if ( showOn === "button" || showOn === "both" ) { // pop-up date
															// picker when
															// button clicked
			buttonText = this._get( inst, "buttonText" );
			buttonImage = this._get( inst, "buttonImage" );
			inst.trigger = $( this._get( inst, "buttonImageOnly" ) ?
				$( "<img/>" ).addClass( this._triggerClass ).
					attr( { src: buttonImage, alt: buttonText, title: buttonText } ) :
				$( "<button type='button'></button>" ).addClass( this._triggerClass ).
					html( !buttonImage ? buttonText : $( "<img/>" ).attr(
					{ src:buttonImage, alt:buttonText, title:buttonText } ) ) );
			input[ isRTL ? "before" : "after" ]( inst.trigger );
			inst.trigger.on( "click", function() {
				if ( $.datepicker._datepickerShowing && $.datepicker._lastInput === input[ 0 ] ) {
					$.datepicker._hideDatepicker();
				} else if ( $.datepicker._datepickerShowing && $.datepicker._lastInput !== input[ 0 ] ) {
					$.datepicker._hideDatepicker();
					$.datepicker._showDatepicker( input[ 0 ] );
				} else {
					$.datepicker._showDatepicker( input[ 0 ] );
				}
				return false;
			} );
		}
	},

	/* Apply the maximum length for the date format. */
	_autoSize: function( inst ) {
		if ( this._get( inst, "autoSize" ) && !inst.inline ) {
			var findMax, max, maxI, i,
				date = new Date( 2009, 12 - 1, 20 ), // Ensure double digits
				dateFormat = this._get( inst, "dateFormat" );

			if ( dateFormat.match( /[DM]/ ) ) {
				findMax = function( names ) {
					max = 0;
					maxI = 0;
					for ( i = 0; i < names.length; i++ ) {
						if ( names[ i ].length > max ) {
							max = names[ i ].length;
							maxI = i;
						}
					}
					return maxI;
				};
				date.setMonth( findMax( this._get( inst, ( dateFormat.match( /MM/ ) ?
					"monthNames" : "monthNamesShort" ) ) ) );
				date.setDate( findMax( this._get( inst, ( dateFormat.match( /DD/ ) ?
					"dayNames" : "dayNamesShort" ) ) ) + 20 - date.getDay() );
			}
			inst.input.attr( "size", this._formatDate( inst, date ).length );
		}
	},

	/* Attach an inline date picker to a div. */
	_inlineDatepicker: function( target, inst ) {
		var divSpan = $( target );
		if ( divSpan.hasClass( this.markerClassName ) ) {
			return;
		}
		divSpan.addClass( this.markerClassName ).append( inst.dpDiv );
		$.data( target, "datepicker", inst );
		this._setDate( inst, this._getDefaultDate( inst ), true );
		this._updateDatepicker( inst );
		this._updateAlternate( inst );

		// If disabled option is true, disable the datepicker before showing it
		// (see ticket #5665)
		if ( inst.settings.disabled ) {
			this._disableDatepicker( target );
		}

		// Set display:block in place of inst.dpDiv.show() which won't work on
		// disconnected elements
		// http://bugs.jqueryui.com/ticket/7552 - A Datepicker created on a
		// detached div has zero height
		inst.dpDiv.css( "display", "block" );
	},

	/*
	 * Pop-up the date picker in a "dialog" box. @param input element - ignored
	 * @param date string or Date - the initial date to display @param onSelect
	 * function - the function to call when a date is selected @param settings
	 * object - update the dialog date picker instance's settings (anonymous
	 * object) @param pos int[2] - coordinates for the dialog's position within
	 * the screen or event - with x/y coordinates or leave empty for default
	 * (screen centre) @return the manager object
	 */
	_dialogDatepicker: function( input, date, onSelect, settings, pos ) {
		var id, browserWidth, browserHeight, scrollX, scrollY,
			inst = this._dialogInst; // internal instance

		if ( !inst ) {
			this.uuid += 1;
			id = "dp" + this.uuid;
			this._dialogInput = $( "<input type='text' id='" + id +
				"' style='position: absolute; top: -100px; width: 0px;'/>" );
			this._dialogInput.on( "keydown", this._doKeyDown );
			$( "body" ).append( this._dialogInput );
			inst = this._dialogInst = this._newInst( this._dialogInput, false );
			inst.settings = {};
			$.data( this._dialogInput[ 0 ], "datepicker", inst );
		}
		datepicker_extendRemove( inst.settings, settings || {} );
		date = ( date && date.constructor === Date ? this._formatDate( inst, date ) : date );
		this._dialogInput.val( date );

		this._pos = ( pos ? ( pos.length ? pos : [ pos.pageX, pos.pageY ] ) : null );
		if ( !this._pos ) {
			browserWidth = document.documentElement.clientWidth;
			browserHeight = document.documentElement.clientHeight;
			scrollX = document.documentElement.scrollLeft || document.body.scrollLeft;
			scrollY = document.documentElement.scrollTop || document.body.scrollTop;
			this._pos = // should use actual width/height below
				[ ( browserWidth / 2 ) - 100 + scrollX, ( browserHeight / 2 ) - 150 + scrollY ];
		}

		// Move input on screen for focus, but hidden behind dialog
		this._dialogInput.css( "left", ( this._pos[ 0 ] + 20 ) + "px" ).css( "top", this._pos[ 1 ] + "px" );
		inst.settings.onSelect = onSelect;
		this._inDialog = true;
		this.dpDiv.addClass( this._dialogClass );
		this._showDatepicker( this._dialogInput[ 0 ] );
		if ( $.blockUI ) {
			$.blockUI( this.dpDiv );
		}
		$.data( this._dialogInput[ 0 ], "datepicker", inst );
		return this;
	},

	/*
	 * Detach a datepicker from its control. @param target element - the target
	 * input field or division or span
	 */
	_destroyDatepicker: function( target ) {
		var nodeName,
			$target = $( target ),
			inst = $.data( target, "datepicker" );

		if ( !$target.hasClass( this.markerClassName ) ) {
			return;
		}

		nodeName = target.nodeName.toLowerCase();
		$.removeData( target, "datepicker" );
		if ( nodeName === "input" ) {
			inst.append.remove();
			inst.trigger.remove();
			$target.removeClass( this.markerClassName ).
				off( "focus", this._showDatepicker ).
				off( "keydown", this._doKeyDown ).
				off( "keypress", this._doKeyPress ).
				off( "keyup", this._doKeyUp );
		} else if ( nodeName === "div" || nodeName === "span" ) {
			$target.removeClass( this.markerClassName ).empty();
		}

		if ( datepicker_instActive === inst ) {
			datepicker_instActive = null;
		}
	},

	/*
	 * Enable the date picker to a jQuery selection. @param target element - the
	 * target input field or division or span
	 */
	_enableDatepicker: function( target ) {
		var nodeName, inline,
			$target = $( target ),
			inst = $.data( target, "datepicker" );

		if ( !$target.hasClass( this.markerClassName ) ) {
			return;
		}

		nodeName = target.nodeName.toLowerCase();
		if ( nodeName === "input" ) {
			target.disabled = false;
			inst.trigger.filter( "button" ).
				each( function() { this.disabled = false; } ).end().
				filter( "img" ).css( { opacity: "1.0", cursor: "" } );
		} else if ( nodeName === "div" || nodeName === "span" ) {
			inline = $target.children( "." + this._inlineClass );
			inline.children().removeClass( "ui-state-disabled" );
			inline.find( "select.ui-datepicker-month, select.ui-datepicker-year" ).
				prop( "disabled", false );
		}
		this._disabledInputs = $.map( this._disabledInputs,
			function( value ) { return ( value === target ? null : value ); } ); // delete
																					// entry
	},

	/*
	 * Disable the date picker to a jQuery selection. @param target element -
	 * the target input field or division or span
	 */
	_disableDatepicker: function( target ) {
		var nodeName, inline,
			$target = $( target ),
			inst = $.data( target, "datepicker" );

		if ( !$target.hasClass( this.markerClassName ) ) {
			return;
		}

		nodeName = target.nodeName.toLowerCase();
		if ( nodeName === "input" ) {
			target.disabled = true;
			inst.trigger.filter( "button" ).
				each( function() { this.disabled = true; } ).end().
				filter( "img" ).css( { opacity: "0.5", cursor: "default" } );
		} else if ( nodeName === "div" || nodeName === "span" ) {
			inline = $target.children( "." + this._inlineClass );
			inline.children().addClass( "ui-state-disabled" );
			inline.find( "select.ui-datepicker-month, select.ui-datepicker-year" ).
				prop( "disabled", true );
		}
		this._disabledInputs = $.map( this._disabledInputs,
			function( value ) { return ( value === target ? null : value ); } ); // delete
																					// entry
		this._disabledInputs[ this._disabledInputs.length ] = target;
	},

	/*
	 * Is the first field in a jQuery collection disabled as a datepicker?
	 * @param target element - the target input field or division or span
	 * @return boolean - true if disabled, false if enabled
	 */
	_isDisabledDatepicker: function( target ) {
		if ( !target ) {
			return false;
		}
		for ( var i = 0; i < this._disabledInputs.length; i++ ) {
			if ( this._disabledInputs[ i ] === target ) {
				return true;
			}
		}
		return false;
	},

	/*
	 * Retrieve the instance data for the target control. @param target element -
	 * the target input field or division or span @return object - the
	 * associated instance data @throws error if a jQuery problem getting data
	 */
	_getInst: function( target ) {
		try {
			return $.data( target, "datepicker" );
		}
		catch ( err ) {
			throw "Missing instance data for this datepicker";
		}
	},

	/*
	 * Update or retrieve the settings for a date picker attached to an input
	 * field or division. @param target element - the target input field or
	 * division or span @param name object - the new settings to update or
	 * string - the name of the setting to change or retrieve, when retrieving
	 * also "all" for all instance settings or "defaults" for all global
	 * defaults @param value any - the new value for the setting (omit if above
	 * is an object or to retrieve a value)
	 */
	_optionDatepicker: function( target, name, value ) {
		var settings, date, minDate, maxDate,
			inst = this._getInst( target );

		if ( arguments.length === 2 && typeof name === "string" ) {
			return ( name === "defaults" ? $.extend( {}, $.datepicker._defaults ) :
				( inst ? ( name === "all" ? $.extend( {}, inst.settings ) :
				this._get( inst, name ) ) : null ) );
		}

		settings = name || {};
		if ( typeof name === "string" ) {
			settings = {};
			settings[ name ] = value;
		}

		if ( inst ) {
			if ( this._curInst === inst ) {
				this._hideDatepicker();
			}

			date = this._getDateDatepicker( target, true );
			minDate = this._getMinMaxDate( inst, "min" );
			maxDate = this._getMinMaxDate( inst, "max" );
			datepicker_extendRemove( inst.settings, settings );

			// reformat the old minDate/maxDate values if dateFormat changes and
			// a new minDate/maxDate isn't provided
			if ( minDate !== null && settings.dateFormat !== undefined && settings.minDate === undefined ) {
				inst.settings.minDate = this._formatDate( inst, minDate );
			}
			if ( maxDate !== null && settings.dateFormat !== undefined && settings.maxDate === undefined ) {
				inst.settings.maxDate = this._formatDate( inst, maxDate );
			}
			if ( "disabled" in settings ) {
				if ( settings.disabled ) {
					this._disableDatepicker( target );
				} else {
					this._enableDatepicker( target );
				}
			}
			this._attachments( $( target ), inst );
			this._autoSize( inst );
			this._setDate( inst, date );
			this._updateAlternate( inst );
			this._updateDatepicker( inst );
		}
	},

	// Change method deprecated
	_changeDatepicker: function( target, name, value ) {
		this._optionDatepicker( target, name, value );
	},

	/*
	 * Redraw the date picker attached to an input field or division. @param
	 * target element - the target input field or division or span
	 */
	_refreshDatepicker: function( target ) {
		var inst = this._getInst( target );
		if ( inst ) {
			this._updateDatepicker( inst );
		}
	},

	/*
	 * Set the dates for a jQuery selection. @param target element - the target
	 * input field or division or span @param date Date - the new date
	 */
	_setDateDatepicker: function( target, date ) {
		var inst = this._getInst( target );
		if ( inst ) {
			this._setDate( inst, date );
			this._updateDatepicker( inst );
			this._updateAlternate( inst );
		}
	},

	/*
	 * Get the date(s) for the first entry in a jQuery selection. @param target
	 * element - the target input field or division or span @param noDefault
	 * boolean - true if no default date is to be used @return Date - the
	 * current date
	 */
	_getDateDatepicker: function( target, noDefault ) {
		var inst = this._getInst( target );
		if ( inst && !inst.inline ) {
			this._setDateFromField( inst, noDefault );
		}
		return ( inst ? this._getDate( inst ) : null );
	},

	/* Handle keystrokes. */
	_doKeyDown: function( event ) {
		var onSelect, dateStr, sel,
			inst = $.datepicker._getInst( event.target ),
			handled = true,
			isRTL = inst.dpDiv.is( ".ui-datepicker-rtl" );

		inst._keyEvent = true;
		if ( $.datepicker._datepickerShowing ) {
			switch ( event.keyCode ) {
				case 9: $.datepicker._hideDatepicker();
						handled = false;
						break; // hide on tab out
				case 13: sel = $( "td." + $.datepicker._dayOverClass + ":not(." +
									$.datepicker._currentClass + ")", inst.dpDiv );
						if ( sel[ 0 ] ) {
							$.datepicker._selectDay( event.target, inst.selectedMonth, inst.selectedYear, sel[ 0 ] );
						}

						onSelect = $.datepicker._get( inst, "onSelect" );
						if ( onSelect ) {
							dateStr = $.datepicker._formatDate( inst );

							// Trigger custom callback
							onSelect.apply( ( inst.input ? inst.input[ 0 ] : null ), [ dateStr, inst ] );
						} else {
							$.datepicker._hideDatepicker();
						}

						return false; // don't submit the form
				case 27: $.datepicker._hideDatepicker();
						break; // hide on escape
				case 33: $.datepicker._adjustDate( event.target, ( event.ctrlKey ?
							-$.datepicker._get( inst, "stepBigMonths" ) :
							-$.datepicker._get( inst, "stepMonths" ) ), "M" );
						break; // previous month/year on page up/+ ctrl
				case 34: $.datepicker._adjustDate( event.target, ( event.ctrlKey ?
							+$.datepicker._get( inst, "stepBigMonths" ) :
							+$.datepicker._get( inst, "stepMonths" ) ), "M" );
						break; // next month/year on page down/+ ctrl
				case 35: if ( event.ctrlKey || event.metaKey ) {
							$.datepicker._clearDate( event.target );
						}
						handled = event.ctrlKey || event.metaKey;
						break; // clear on ctrl or command +end
				case 36: if ( event.ctrlKey || event.metaKey ) {
							$.datepicker._gotoToday( event.target );
						}
						handled = event.ctrlKey || event.metaKey;
						break; // current on ctrl or command +home
				case 37: if ( event.ctrlKey || event.metaKey ) {
							$.datepicker._adjustDate( event.target, ( isRTL ? +1 : -1 ), "D" );
						}
						handled = event.ctrlKey || event.metaKey;

						// -1 day on ctrl or command +left
						if ( event.originalEvent.altKey ) {
							$.datepicker._adjustDate( event.target, ( event.ctrlKey ?
								-$.datepicker._get( inst, "stepBigMonths" ) :
								-$.datepicker._get( inst, "stepMonths" ) ), "M" );
						}

						// next month/year on alt +left on Mac
						break;
				case 38: if ( event.ctrlKey || event.metaKey ) {
							$.datepicker._adjustDate( event.target, -7, "D" );
						}
						handled = event.ctrlKey || event.metaKey;
						break; // -1 week on ctrl or command +up
				case 39: if ( event.ctrlKey || event.metaKey ) {
							$.datepicker._adjustDate( event.target, ( isRTL ? -1 : +1 ), "D" );
						}
						handled = event.ctrlKey || event.metaKey;

						// +1 day on ctrl or command +right
						if ( event.originalEvent.altKey ) {
							$.datepicker._adjustDate( event.target, ( event.ctrlKey ?
								+$.datepicker._get( inst, "stepBigMonths" ) :
								+$.datepicker._get( inst, "stepMonths" ) ), "M" );
						}

						// next month/year on alt +right
						break;
				case 40: if ( event.ctrlKey || event.metaKey ) {
							$.datepicker._adjustDate( event.target, +7, "D" );
						}
						handled = event.ctrlKey || event.metaKey;
						break; // +1 week on ctrl or command +down
				default: handled = false;
			}
		} else if ( event.keyCode === 36 && event.ctrlKey ) { // display the
																// date picker
																// on ctrl+home
			$.datepicker._showDatepicker( this );
		} else {
			handled = false;
		}

		if ( handled ) {
			event.preventDefault();
			event.stopPropagation();
		}
	},

	/* Filter entered characters - based on date format. */
	_doKeyPress: function( event ) {
		var chars, chr,
			inst = $.datepicker._getInst( event.target );

		if ( $.datepicker._get( inst, "constrainInput" ) ) {
			chars = $.datepicker._possibleChars( $.datepicker._get( inst, "dateFormat" ) );
			chr = String.fromCharCode( event.charCode == null ? event.keyCode : event.charCode );
			return event.ctrlKey || event.metaKey || ( chr < " " || !chars || chars.indexOf( chr ) > -1 );
		}
	},

	/* Synchronise manual entry and field/alternate field. */
	_doKeyUp: function( event ) {
		var date,
			inst = $.datepicker._getInst( event.target );

		if ( inst.input.val() !== inst.lastVal ) {
			try {
				date = $.datepicker.parseDate( $.datepicker._get( inst, "dateFormat" ),
					( inst.input ? inst.input.val() : null ),
					$.datepicker._getFormatConfig( inst ) );

				if ( date ) { // only if valid
					$.datepicker._setDateFromField( inst );
					$.datepicker._updateAlternate( inst );
					$.datepicker._updateDatepicker( inst );
				}
			}
			catch ( err ) {
			}
		}
		return true;
	},

	/*
	 * Pop-up the date picker for a given input field. If false returned from
	 * beforeShow event handler do not show. @param input element - the input
	 * field attached to the date picker or event - if triggered by focus
	 */
	_showDatepicker: function( input ) {
		input = input.target || input;
		if ( input.nodeName.toLowerCase() !== "input" ) { // find from
															// button/image
															// trigger
			input = $( "input", input.parentNode )[ 0 ];
		}

		if ( $.datepicker._isDisabledDatepicker( input ) || $.datepicker._lastInput === input ) { // already
																									// here
			return;
		}

		var inst, beforeShow, beforeShowSettings, isFixed,
			offset, showAnim, duration;

		inst = $.datepicker._getInst( input );
		if ( $.datepicker._curInst && $.datepicker._curInst !== inst ) {
			$.datepicker._curInst.dpDiv.stop( true, true );
			if ( inst && $.datepicker._datepickerShowing ) {
				$.datepicker._hideDatepicker( $.datepicker._curInst.input[ 0 ] );
			}
		}

		beforeShow = $.datepicker._get( inst, "beforeShow" );
		beforeShowSettings = beforeShow ? beforeShow.apply( input, [ input, inst ] ) : {};
		if ( beforeShowSettings === false ) {
			return;
		}
		datepicker_extendRemove( inst.settings, beforeShowSettings );

		inst.lastVal = null;
		$.datepicker._lastInput = input;
		$.datepicker._setDateFromField( inst );

		if ( $.datepicker._inDialog ) { // hide cursor
			input.value = "";
		}
		if ( !$.datepicker._pos ) { // position below input
			$.datepicker._pos = $.datepicker._findPos( input );
			$.datepicker._pos[ 1 ] += input.offsetHeight; // add the height
		}

		isFixed = false;
		$( input ).parents().each( function() {
			isFixed |= $( this ).css( "position" ) === "fixed";
			return !isFixed;
		} );

		offset = { left: $.datepicker._pos[ 0 ], top: $.datepicker._pos[ 1 ] };
		$.datepicker._pos = null;

		// to avoid flashes on Firefox
		inst.dpDiv.empty();

		// determine sizing offscreen
		inst.dpDiv.css( { position: "absolute", display: "block", top: "-1000px" } );
		$.datepicker._updateDatepicker( inst );

		// fix width for dynamic number of date pickers
		// and adjust position before showing
		offset = $.datepicker._checkOffset( inst, offset, isFixed );
		inst.dpDiv.css( { position: ( $.datepicker._inDialog && $.blockUI ?
			"static" : ( isFixed ? "fixed" : "absolute" ) ), display: "none",
			left: offset.left + "px", top: offset.top + "px" } );

		if ( !inst.inline ) {
			showAnim = $.datepicker._get( inst, "showAnim" );
			duration = $.datepicker._get( inst, "duration" );
			inst.dpDiv.css( "z-index", datepicker_getZindex( $( input ) ) + 1 );
			$.datepicker._datepickerShowing = true;

			if ( $.effects && $.effects.effect[ showAnim ] ) {
				inst.dpDiv.show( showAnim, $.datepicker._get( inst, "showOptions" ), duration );
			} else {
				inst.dpDiv[ showAnim || "show" ]( showAnim ? duration : null );
			}

			if ( $.datepicker._shouldFocusInput( inst ) ) {
				inst.input.trigger( "focus" );
			}

			$.datepicker._curInst = inst;
		}
	},

	/* Generate the date picker content. */
	_updateDatepicker: function( inst ) {
		this.maxRows = 4; // Reset the max number of rows being displayed (see
							// #7043)
		datepicker_instActive = inst; // for delegate hover events
		inst.dpDiv.empty().append( this._generateHTML( inst ) );
		this._attachHandlers( inst );

		var origyearshtml,
			numMonths = this._getNumberOfMonths( inst ),
			cols = numMonths[ 1 ],
			width = 17,
			activeCell = inst.dpDiv.find( "." + this._dayOverClass + " a" );

		if ( activeCell.length > 0 ) {
			datepicker_handleMouseover.apply( activeCell.get( 0 ) );
		}

		inst.dpDiv.removeClass( "ui-datepicker-multi-2 ui-datepicker-multi-3 ui-datepicker-multi-4" ).width( "" );
		if ( cols > 1 ) {
			inst.dpDiv.addClass( "ui-datepicker-multi-" + cols ).css( "width", ( width * cols ) + "em" );
		}
		inst.dpDiv[ ( numMonths[ 0 ] !== 1 || numMonths[ 1 ] !== 1 ? "add" : "remove" ) +
			"Class" ]( "ui-datepicker-multi" );
		inst.dpDiv[ ( this._get( inst, "isRTL" ) ? "add" : "remove" ) +
			"Class" ]( "ui-datepicker-rtl" );

		if ( inst === $.datepicker._curInst && $.datepicker._datepickerShowing && $.datepicker._shouldFocusInput( inst ) ) {
			inst.input.trigger( "focus" );
		}

		// Deffered render of the years select (to avoid flashes on Firefox)
		if ( inst.yearshtml ) {
			origyearshtml = inst.yearshtml;
			setTimeout( function() {

				// assure that inst.yearshtml didn't change.
				if ( origyearshtml === inst.yearshtml && inst.yearshtml ) {
					inst.dpDiv.find( "select.ui-datepicker-year:first" ).replaceWith( inst.yearshtml );
				}
				origyearshtml = inst.yearshtml = null;
			}, 0 );
		}
	},

	// #6694 - don't focus the input if it's already focused
	// this breaks the change event in IE
	// Support: IE and jQuery <1.9
	_shouldFocusInput: function( inst ) {
		return inst.input && inst.input.is( ":visible" ) && !inst.input.is( ":disabled" ) && !inst.input.is( ":focus" );
	},

	/* Check positioning to remain on screen. */
	_checkOffset: function( inst, offset, isFixed ) {
		var dpWidth = inst.dpDiv.outerWidth(),
			dpHeight = inst.dpDiv.outerHeight(),
			inputWidth = inst.input ? inst.input.outerWidth() : 0,
			inputHeight = inst.input ? inst.input.outerHeight() : 0,
			viewWidth = document.documentElement.clientWidth + ( isFixed ? 0 : $( document ).scrollLeft() ),
			viewHeight = document.documentElement.clientHeight + ( isFixed ? 0 : $( document ).scrollTop() );

		offset.left -= ( this._get( inst, "isRTL" ) ? ( dpWidth - inputWidth ) : 0 );
		offset.left -= ( isFixed && offset.left === inst.input.offset().left ) ? $( document ).scrollLeft() : 0;
		offset.top -= ( isFixed && offset.top === ( inst.input.offset().top + inputHeight ) ) ? $( document ).scrollTop() : 0;

		// Now check if datepicker is showing outside window viewport - move to
		// a better place if so.
		offset.left -= Math.min( offset.left, ( offset.left + dpWidth > viewWidth && viewWidth > dpWidth ) ?
			Math.abs( offset.left + dpWidth - viewWidth ) : 0 );
		offset.top -= Math.min( offset.top, ( offset.top + dpHeight > viewHeight && viewHeight > dpHeight ) ?
			Math.abs( dpHeight + inputHeight ) : 0 );

		return offset;
	},

	/* Find an object's position on the screen. */
	_findPos: function( obj ) {
		var position,
			inst = this._getInst( obj ),
			isRTL = this._get( inst, "isRTL" );

		while ( obj && ( obj.type === "hidden" || obj.nodeType !== 1 || $.expr.filters.hidden( obj ) ) ) {
			obj = obj[ isRTL ? "previousSibling" : "nextSibling" ];
		}

		position = $( obj ).offset();
		return [ position.left, position.top ];
	},

	/*
	 * Hide the date picker from view. @param input element - the input field
	 * attached to the date picker
	 */
	_hideDatepicker: function( input ) {
		var showAnim, duration, postProcess, onClose,
			inst = this._curInst;

		if ( !inst || ( input && inst !== $.data( input, "datepicker" ) ) ) {
			return;
		}

		if ( this._datepickerShowing ) {
			showAnim = this._get( inst, "showAnim" );
			duration = this._get( inst, "duration" );
			postProcess = function() {
				$.datepicker._tidyDialog( inst );
			};

			// DEPRECATED: after BC for 1.8.x $.effects[ showAnim ] is not
			// needed
			if ( $.effects && ( $.effects.effect[ showAnim ] || $.effects[ showAnim ] ) ) {
				inst.dpDiv.hide( showAnim, $.datepicker._get( inst, "showOptions" ), duration, postProcess );
			} else {
				inst.dpDiv[ ( showAnim === "slideDown" ? "slideUp" :
					( showAnim === "fadeIn" ? "fadeOut" : "hide" ) ) ]( ( showAnim ? duration : null ), postProcess );
			}

			if ( !showAnim ) {
				postProcess();
			}
			this._datepickerShowing = false;

			onClose = this._get( inst, "onClose" );
			if ( onClose ) {
				onClose.apply( ( inst.input ? inst.input[ 0 ] : null ), [ ( inst.input ? inst.input.val() : "" ), inst ] );
			}

			this._lastInput = null;
			if ( this._inDialog ) {
				this._dialogInput.css( { position: "absolute", left: "0", top: "-100px" } );
				if ( $.blockUI ) {
					$.unblockUI();
					$( "body" ).append( this.dpDiv );
				}
			}
			this._inDialog = false;
		}
	},

	/* Tidy up after a dialog display. */
	_tidyDialog: function( inst ) {
		inst.dpDiv.removeClass( this._dialogClass ).off( ".ui-datepicker-calendar" );
	},

	/* Close date picker if clicked elsewhere. */
	_checkExternalClick: function( event ) {
		if ( !$.datepicker._curInst ) {
			return;
		}

		var $target = $( event.target ),
			inst = $.datepicker._getInst( $target[ 0 ] );

		if ( ( ( $target[ 0 ].id !== $.datepicker._mainDivId &&
				$target.parents( "#" + $.datepicker._mainDivId ).length === 0 &&
				!$target.hasClass( $.datepicker.markerClassName ) &&
				!$target.closest( "." + $.datepicker._triggerClass ).length &&
				$.datepicker._datepickerShowing && !( $.datepicker._inDialog && $.blockUI ) ) ) ||
			( $target.hasClass( $.datepicker.markerClassName ) && $.datepicker._curInst !== inst ) ) {
				$.datepicker._hideDatepicker();
		}
	},

	/* Adjust one of the date sub-fields. */
	_adjustDate: function( id, offset, period ) {
		var target = $( id ),
			inst = this._getInst( target[ 0 ] );

		if ( this._isDisabledDatepicker( target[ 0 ] ) ) {
			return;
		}
		this._adjustInstDate( inst, offset +
			( period === "M" ? this._get( inst, "showCurrentAtPos" ) : 0 ), // undo
																			// positioning
			period );
		this._updateDatepicker( inst );
	},

	/* Action for current link. */
	_gotoToday: function( id ) {
		var date,
			target = $( id ),
			inst = this._getInst( target[ 0 ] );

		if ( this._get( inst, "gotoCurrent" ) && inst.currentDay ) {
			inst.selectedDay = inst.currentDay;
			inst.drawMonth = inst.selectedMonth = inst.currentMonth;
			inst.drawYear = inst.selectedYear = inst.currentYear;
		} else {
			date = new Date();
			inst.selectedDay = date.getDate();
			inst.drawMonth = inst.selectedMonth = date.getMonth();
			inst.drawYear = inst.selectedYear = date.getFullYear();
		}
		this._notifyChange( inst );
		this._adjustDate( target );
	},

	/* Action for selecting a new month/year. */
	_selectMonthYear: function( id, select, period ) {
		var target = $( id ),
			inst = this._getInst( target[ 0 ] );

		inst[ "selected" + ( period === "M" ? "Month" : "Year" ) ] =
		inst[ "draw" + ( period === "M" ? "Month" : "Year" ) ] =
			parseInt( select.options[ select.selectedIndex ].value, 10 );

		this._notifyChange( inst );
		this._adjustDate( target );
	},

	/* Action for selecting a day. */
	_selectDay: function( id, month, year, td ) {
		var inst,
			target = $( id );

		if ( $( td ).hasClass( this._unselectableClass ) || this._isDisabledDatepicker( target[ 0 ] ) ) {
			return;
		}

		inst = this._getInst( target[ 0 ] );
		inst.selectedDay = inst.currentDay = $( "a", td ).html();
		inst.selectedMonth = inst.currentMonth = month;
		inst.selectedYear = inst.currentYear = year;
		this._selectDate( id, this._formatDate( inst,
			inst.currentDay, inst.currentMonth, inst.currentYear ) );
	},

	/* Erase the input field and hide the date picker. */
	_clearDate: function( id ) {
		var target = $( id );
		this._selectDate( target, "" );
	},

	/* Update the input field with the selected date. */
	_selectDate: function( id, dateStr ) {
		var onSelect,
			target = $( id ),
			inst = this._getInst( target[ 0 ] );

		dateStr = ( dateStr != null ? dateStr : this._formatDate( inst ) );
		if ( inst.input ) {
			inst.input.val( dateStr );
		}
		this._updateAlternate( inst );

		onSelect = this._get( inst, "onSelect" );
		if ( onSelect ) {
			onSelect.apply( ( inst.input ? inst.input[ 0 ] : null ), [ dateStr, inst ] );  // trigger
																							// custom
																							// callback
		} else if ( inst.input ) {
			inst.input.trigger( "change" ); // fire the change event
		}

		if ( inst.inline ) {
			this._updateDatepicker( inst );
		} else {
			this._hideDatepicker();
			this._lastInput = inst.input[ 0 ];
			if ( typeof( inst.input[ 0 ] ) !== "object" ) {
				inst.input.trigger( "focus" ); // restore focus
			}
			this._lastInput = null;
		}
	},

	/* Update any alternate field to synchronise with the main field. */
	_updateAlternate: function( inst ) {
		var altFormat, date, dateStr,
			altField = this._get( inst, "altField" );

		if ( altField ) { // update alternate field too
			altFormat = this._get( inst, "altFormat" ) || this._get( inst, "dateFormat" );
			date = this._getDate( inst );
			dateStr = this.formatDate( altFormat, date, this._getFormatConfig( inst ) );
			$( altField ).val( dateStr );
		}
	},

	/*
	 * Set as beforeShowDay function to prevent selection of weekends. @param
	 * date Date - the date to customise @return [boolean, string] - is this
	 * date selectable?, what is its CSS class?
	 */
	noWeekends: function( date ) {
		var day = date.getDay();
		return [ ( day > 0 && day < 6 ), "" ];
	},

	/*
	 * Set as calculateWeek to determine the week of the year based on the ISO
	 * 8601 definition. @param date Date - the date to get the week for @return
	 * number - the number of the week within the year that contains this date
	 */
	iso8601Week: function( date ) {
		var time,
			checkDate = new Date( date.getTime() );

		// Find Thursday of this week starting on Monday
		checkDate.setDate( checkDate.getDate() + 4 - ( checkDate.getDay() || 7 ) );

		time = checkDate.getTime();
		checkDate.setMonth( 0 ); // Compare with Jan 1
		checkDate.setDate( 1 );
		return Math.floor( Math.round( ( time - checkDate ) / 86400000 ) / 7 ) + 1;
	},

	/*
	 * Parse a string value into a date object. See formatDate below for the
	 * possible formats.
	 * 
	 * @param format string - the expected format of the date @param value
	 * string - the date in the above format @param settings Object - attributes
	 * include: shortYearCutoff number - the cutoff year for determining the
	 * century (optional) dayNamesShort string[7] - abbreviated names of the
	 * days from Sunday (optional) dayNames string[7] - names of the days from
	 * Sunday (optional) monthNamesShort string[12] - abbreviated names of the
	 * months (optional) monthNames string[12] - names of the months (optional)
	 * @return Date - the extracted date value or null if value is blank
	 */
	parseDate: function( format, value, settings ) {
		if ( format == null || value == null ) {
			throw "Invalid arguments";
		}

		value = ( typeof value === "object" ? value.toString() : value + "" );
		if ( value === "" ) {
			return null;
		}

		var iFormat, dim, extra,
			iValue = 0,
			shortYearCutoffTemp = ( settings ? settings.shortYearCutoff : null ) || this._defaults.shortYearCutoff,
			shortYearCutoff = ( typeof shortYearCutoffTemp !== "string" ? shortYearCutoffTemp :
				new Date().getFullYear() % 100 + parseInt( shortYearCutoffTemp, 10 ) ),
			dayNamesShort = ( settings ? settings.dayNamesShort : null ) || this._defaults.dayNamesShort,
			dayNames = ( settings ? settings.dayNames : null ) || this._defaults.dayNames,
			monthNamesShort = ( settings ? settings.monthNamesShort : null ) || this._defaults.monthNamesShort,
			monthNames = ( settings ? settings.monthNames : null ) || this._defaults.monthNames,
			year = -1,
			month = -1,
			day = -1,
			doy = -1,
			literal = false,
			date,

			// Check whether a format character is doubled
			lookAhead = function( match ) {
				var matches = ( iFormat + 1 < format.length && format.charAt( iFormat + 1 ) === match );
				if ( matches ) {
					iFormat++;
				}
				return matches;
			},

			// Extract a number from the string value
			getNumber = function( match ) {
				var isDoubled = lookAhead( match ),
					size = ( match === "@" ? 14 : ( match === "!" ? 20 :
					( match === "y" && isDoubled ? 4 : ( match === "o" ? 3 : 2 ) ) ) ),
					minSize = ( match === "y" ? size : 1 ),
					digits = new RegExp( "^\\d{" + minSize + "," + size + "}" ),
					num = value.substring( iValue ).match( digits );
				if ( !num ) {
					throw "Missing number at position " + iValue;
				}
				iValue += num[ 0 ].length;
				return parseInt( num[ 0 ], 10 );
			},

			// Extract a name from the string value and convert to an index
			getName = function( match, shortNames, longNames ) {
				var index = -1,
					names = $.map( lookAhead( match ) ? longNames : shortNames, function( v, k ) {
						return [ [ k, v ] ];
					} ).sort( function( a, b ) {
						return -( a[ 1 ].length - b[ 1 ].length );
					} );

				$.each( names, function( i, pair ) {
					var name = pair[ 1 ];
					if ( value.substr( iValue, name.length ).toLowerCase() === name.toLowerCase() ) {
						index = pair[ 0 ];
						iValue += name.length;
						return false;
					}
				} );
				if ( index !== -1 ) {
					return index + 1;
				} else {
					throw "Unknown name at position " + iValue;
				}
			},

			// Confirm that a literal character matches the string value
			checkLiteral = function() {
				if ( value.charAt( iValue ) !== format.charAt( iFormat ) ) {
					throw "Unexpected literal at position " + iValue;
				}
				iValue++;
			};

		for ( iFormat = 0; iFormat < format.length; iFormat++ ) {
			if ( literal ) {
				if ( format.charAt( iFormat ) === "'" && !lookAhead( "'" ) ) {
					literal = false;
				} else {
					checkLiteral();
				}
			} else {
				switch ( format.charAt( iFormat ) ) {
					case "d":
						day = getNumber( "d" );
						break;
					case "D":
						getName( "D", dayNamesShort, dayNames );
						break;
					case "o":
						doy = getNumber( "o" );
						break;
					case "m":
						month = getNumber( "m" );
						break;
					case "M":
						month = getName( "M", monthNamesShort, monthNames );
						break;
					case "y":
						year = getNumber( "y" );
						break;
					case "@":
						date = new Date( getNumber( "@" ) );
						year = date.getFullYear();
						month = date.getMonth() + 1;
						day = date.getDate();
						break;
					case "!":
						date = new Date( ( getNumber( "!" ) - this._ticksTo1970 ) / 10000 );
						year = date.getFullYear();
						month = date.getMonth() + 1;
						day = date.getDate();
						break;
					case "'":
						if ( lookAhead( "'" ) ) {
							checkLiteral();
						} else {
							literal = true;
						}
						break;
					default:
						checkLiteral();
				}
			}
		}

		if ( iValue < value.length ) {
			extra = value.substr( iValue );
			if ( !/^\s+/.test( extra ) ) {
				throw "Extra/unparsed characters found in date: " + extra;
			}
		}

		if ( year === -1 ) {
			year = new Date().getFullYear();
		} else if ( year < 100 ) {
			year += new Date().getFullYear() - new Date().getFullYear() % 100 +
				( year <= shortYearCutoff ? 0 : -100 );
		}

		if ( doy > -1 ) {
			month = 1;
			day = doy;
			do {
				dim = this._getDaysInMonth( year, month - 1 );
				if ( day <= dim ) {
					break;
				}
				month++;
				day -= dim;
			} while ( true );
		}

		date = this._daylightSavingAdjust( new Date( year, month - 1, day ) );
		if ( date.getFullYear() !== year || date.getMonth() + 1 !== month || date.getDate() !== day ) {
			throw "Invalid date"; // E.g. 31/02/00
		}
		return date;
	},

	/* Standard date formats. */
	ATOM: "yy-mm-dd", // RFC 3339 (ISO 8601)
	COOKIE: "D, dd M yy",
	ISO_8601: "yy-mm-dd",
	RFC_822: "D, d M y",
	RFC_850: "DD, dd-M-y",
	RFC_1036: "D, d M y",
	RFC_1123: "D, d M yy",
	RFC_2822: "D, d M yy",
	RSS: "D, d M y", // RFC 822
	TICKS: "!",
	TIMESTAMP: "@",
	W3C: "yy-mm-dd", // ISO 8601

	_ticksTo1970: ( ( ( 1970 - 1 ) * 365 + Math.floor( 1970 / 4 ) - Math.floor( 1970 / 100 ) +
		Math.floor( 1970 / 400 ) ) * 24 * 60 * 60 * 10000000 ),

	/*
	 * Format a date object into a string value. The format can be combinations
	 * of the following: d - day of month (no leading zero) dd - day of month
	 * (two digit) o - day of year (no leading zeros) oo - day of year (three
	 * digit) D - day name short DD - day name long m - month of year (no
	 * leading zero) mm - month of year (two digit) M - month name short MM -
	 * month name long y - year (two digit) yy - year (four digit) @ - Unix
	 * timestamp (ms since 01/01/1970) ! - Windows ticks (100ns since
	 * 01/01/0001) "..." - literal text '' - single quote
	 * 
	 * @param format string - the desired format of the date @param date Date -
	 * the date value to format @param settings Object - attributes include:
	 * dayNamesShort string[7] - abbreviated names of the days from Sunday
	 * (optional) dayNames string[7] - names of the days from Sunday (optional)
	 * monthNamesShort string[12] - abbreviated names of the months (optional)
	 * monthNames string[12] - names of the months (optional) @return string -
	 * the date in the above format
	 */
	formatDate: function( format, date, settings ) {
		if ( !date ) {
			return "";
		}

		var iFormat,
			dayNamesShort = ( settings ? settings.dayNamesShort : null ) || this._defaults.dayNamesShort,
			dayNames = ( settings ? settings.dayNames : null ) || this._defaults.dayNames,
			monthNamesShort = ( settings ? settings.monthNamesShort : null ) || this._defaults.monthNamesShort,
			monthNames = ( settings ? settings.monthNames : null ) || this._defaults.monthNames,

			// Check whether a format character is doubled
			lookAhead = function( match ) {
				var matches = ( iFormat + 1 < format.length && format.charAt( iFormat + 1 ) === match );
				if ( matches ) {
					iFormat++;
				}
				return matches;
			},

			// Format a number, with leading zero if necessary
			formatNumber = function( match, value, len ) {
				var num = "" + value;
				if ( lookAhead( match ) ) {
					while ( num.length < len ) {
						num = "0" + num;
					}
				}
				return num;
			},

			// Format a name, short or long as requested
			formatName = function( match, value, shortNames, longNames ) {
				return ( lookAhead( match ) ? longNames[ value ] : shortNames[ value ] );
			},
			output = "",
			literal = false;

		if ( date ) {
			for ( iFormat = 0; iFormat < format.length; iFormat++ ) {
				if ( literal ) {
					if ( format.charAt( iFormat ) === "'" && !lookAhead( "'" ) ) {
						literal = false;
					} else {
						output += format.charAt( iFormat );
					}
				} else {
					switch ( format.charAt( iFormat ) ) {
						case "d":
							output += formatNumber( "d", date.getDate(), 2 );
							break;
						case "D":
							output += formatName( "D", date.getDay(), dayNamesShort, dayNames );
							break;
						case "o":
							output += formatNumber( "o",
								Math.round( ( new Date( date.getFullYear(), date.getMonth(), date.getDate() ).getTime() - new Date( date.getFullYear(), 0, 0 ).getTime() ) / 86400000 ), 3 );
							break;
						case "m":
							output += formatNumber( "m", date.getMonth() + 1, 2 );
							break;
						case "M":
							output += formatName( "M", date.getMonth(), monthNamesShort, monthNames );
							break;
						case "y":
							output += ( lookAhead( "y" ) ? date.getFullYear() :
								( date.getFullYear() % 100 < 10 ? "0" : "" ) + date.getFullYear() % 100 );
							break;
						case "@":
							output += date.getTime();
							break;
						case "!":
							output += date.getTime() * 10000 + this._ticksTo1970;
							break;
						case "'":
							if ( lookAhead( "'" ) ) {
								output += "'";
							} else {
								literal = true;
							}
							break;
						default:
							output += format.charAt( iFormat );
					}
				}
			}
		}
		return output;
	},

	/* Extract all possible characters from the date format. */
	_possibleChars: function( format ) {
		var iFormat,
			chars = "",
			literal = false,

			// Check whether a format character is doubled
			lookAhead = function( match ) {
				var matches = ( iFormat + 1 < format.length && format.charAt( iFormat + 1 ) === match );
				if ( matches ) {
					iFormat++;
				}
				return matches;
			};

		for ( iFormat = 0; iFormat < format.length; iFormat++ ) {
			if ( literal ) {
				if ( format.charAt( iFormat ) === "'" && !lookAhead( "'" ) ) {
					literal = false;
				} else {
					chars += format.charAt( iFormat );
				}
			} else {
				switch ( format.charAt( iFormat ) ) {
					case "d": case "m": case "y": case "@":
						chars += "0123456789";
						break;
					case "D": case "M":
						return null; // Accept anything
					case "'":
						if ( lookAhead( "'" ) ) {
							chars += "'";
						} else {
							literal = true;
						}
						break;
					default:
						chars += format.charAt( iFormat );
				}
			}
		}
		return chars;
	},

	/* Get a setting value, defaulting if necessary. */
	_get: function( inst, name ) {
		return inst.settings[ name ] !== undefined ?
			inst.settings[ name ] : this._defaults[ name ];
	},

	/* Parse existing date and initialise date picker. */
	_setDateFromField: function( inst, noDefault ) {
		if ( inst.input.val() === inst.lastVal ) {
			return;
		}

		var dateFormat = this._get( inst, "dateFormat" ),
			dates = inst.lastVal = inst.input ? inst.input.val() : null,
			defaultDate = this._getDefaultDate( inst ),
			date = defaultDate,
			settings = this._getFormatConfig( inst );

		try {
			date = this.parseDate( dateFormat, dates, settings ) || defaultDate;
		} catch ( event ) {
			dates = ( noDefault ? "" : dates );
		}
		inst.selectedDay = date.getDate();
		inst.drawMonth = inst.selectedMonth = date.getMonth();
		inst.drawYear = inst.selectedYear = date.getFullYear();
		inst.currentDay = ( dates ? date.getDate() : 0 );
		inst.currentMonth = ( dates ? date.getMonth() : 0 );
		inst.currentYear = ( dates ? date.getFullYear() : 0 );
		this._adjustInstDate( inst );
	},

	/* Retrieve the default date shown on opening. */
	_getDefaultDate: function( inst ) {
		return this._restrictMinMax( inst,
			this._determineDate( inst, this._get( inst, "defaultDate" ), new Date() ) );
	},

	/* A date may be specified as an exact value or a relative one. */
	_determineDate: function( inst, date, defaultDate ) {
		var offsetNumeric = function( offset ) {
				var date = new Date();
				date.setDate( date.getDate() + offset );
				return date;
			},
			offsetString = function( offset ) {
				try {
					return $.datepicker.parseDate( $.datepicker._get( inst, "dateFormat" ),
						offset, $.datepicker._getFormatConfig( inst ) );
				}
				catch ( e ) {

					// Ignore
				}

				var date = ( offset.toLowerCase().match( /^c/ ) ?
					$.datepicker._getDate( inst ) : null ) || new Date(),
					year = date.getFullYear(),
					month = date.getMonth(),
					day = date.getDate(),
					pattern = /([+\-]?[0-9]+)\s*(d|D|w|W|m|M|y|Y)?/g,
					matches = pattern.exec( offset );

				while ( matches ) {
					switch ( matches[ 2 ] || "d" ) {
						case "d" : case "D" :
							day += parseInt( matches[ 1 ], 10 ); break;
						case "w" : case "W" :
							day += parseInt( matches[ 1 ], 10 ) * 7; break;
						case "m" : case "M" :
							month += parseInt( matches[ 1 ], 10 );
							day = Math.min( day, $.datepicker._getDaysInMonth( year, month ) );
							break;
						case "y": case "Y" :
							year += parseInt( matches[ 1 ], 10 );
							day = Math.min( day, $.datepicker._getDaysInMonth( year, month ) );
							break;
					}
					matches = pattern.exec( offset );
				}
				return new Date( year, month, day );
			},
			newDate = ( date == null || date === "" ? defaultDate : ( typeof date === "string" ? offsetString( date ) :
				( typeof date === "number" ? ( isNaN( date ) ? defaultDate : offsetNumeric( date ) ) : new Date( date.getTime() ) ) ) );

		newDate = ( newDate && newDate.toString() === "Invalid Date" ? defaultDate : newDate );
		if ( newDate ) {
			newDate.setHours( 0 );
			newDate.setMinutes( 0 );
			newDate.setSeconds( 0 );
			newDate.setMilliseconds( 0 );
		}
		return this._daylightSavingAdjust( newDate );
	},

	/*
	 * Handle switch to/from daylight saving. Hours may be non-zero on daylight
	 * saving cut-over: > 12 when midnight changeover, but then cannot generate
	 * midnight datetime, so jump to 1AM, otherwise reset. @param date (Date)
	 * the date to check @return (Date) the corrected date
	 */
	_daylightSavingAdjust: function( date ) {
		if ( !date ) {
			return null;
		}
		date.setHours( date.getHours() > 12 ? date.getHours() + 2 : 0 );
		return date;
	},

	/* Set the date(s) directly. */
	_setDate: function( inst, date, noChange ) {
		var clear = !date,
			origMonth = inst.selectedMonth,
			origYear = inst.selectedYear,
			newDate = this._restrictMinMax( inst, this._determineDate( inst, date, new Date() ) );

		inst.selectedDay = inst.currentDay = newDate.getDate();
		inst.drawMonth = inst.selectedMonth = inst.currentMonth = newDate.getMonth();
		inst.drawYear = inst.selectedYear = inst.currentYear = newDate.getFullYear();
		if ( ( origMonth !== inst.selectedMonth || origYear !== inst.selectedYear ) && !noChange ) {
			this._notifyChange( inst );
		}
		this._adjustInstDate( inst );
		if ( inst.input ) {
			inst.input.val( clear ? "" : this._formatDate( inst ) );
		}
	},

	/* Retrieve the date(s) directly. */
	_getDate: function( inst ) {
		var startDate = ( !inst.currentYear || ( inst.input && inst.input.val() === "" ) ? null :
			this._daylightSavingAdjust( new Date(
			inst.currentYear, inst.currentMonth, inst.currentDay ) ) );
			return startDate;
	},

	/*
	 * Attach the onxxx handlers. These are declared statically so they work
	 * with static code transformers like Caja.
	 */
	_attachHandlers: function( inst ) {
		var stepMonths = this._get( inst, "stepMonths" ),
			id = "#" + inst.id.replace( /\\\\/g, "\\" );
		inst.dpDiv.find( "[data-handler]" ).map( function() {
			var handler = {
				prev: function() {
					$.datepicker._adjustDate( id, -stepMonths, "M" );
				},
				next: function() {
					$.datepicker._adjustDate( id, +stepMonths, "M" );
				},
				hide: function() {
					$.datepicker._hideDatepicker();
				},
				today: function() {
					$.datepicker._gotoToday( id );
				},
				selectDay: function() {
					$.datepicker._selectDay( id, +this.getAttribute( "data-month" ), +this.getAttribute( "data-year" ), this );
					return false;
				},
				selectMonth: function() {
					$.datepicker._selectMonthYear( id, this, "M" );
					return false;
				},
				selectYear: function() {
					$.datepicker._selectMonthYear( id, this, "Y" );
					return false;
				}
			};
			$( this ).on( this.getAttribute( "data-event" ), handler[ this.getAttribute( "data-handler" ) ] );
		} );
	},

	/* Generate the HTML for the current state of the date picker. */
	_generateHTML: function( inst ) {
		var maxDraw, prevText, prev, nextText, next, currentText, gotoDate,
			controls, buttonPanel, firstDay, showWeek, dayNames, dayNamesMin,
			monthNames, monthNamesShort, beforeShowDay, showOtherMonths,
			selectOtherMonths, defaultDate, html, dow, row, group, col, selectedDate,
			cornerClass, calender, thead, day, daysInMonth, leadDays, curRows, numRows,
			printDate, dRow, tbody, daySettings, otherMonth, unselectable,
			tempDate = new Date(),
			today = this._daylightSavingAdjust(
				new Date( tempDate.getFullYear(), tempDate.getMonth(), tempDate.getDate() ) ), // clear
																								// time
			isRTL = this._get( inst, "isRTL" ),
			showButtonPanel = this._get( inst, "showButtonPanel" ),
			hideIfNoPrevNext = this._get( inst, "hideIfNoPrevNext" ),
			navigationAsDateFormat = this._get( inst, "navigationAsDateFormat" ),
			numMonths = this._getNumberOfMonths( inst ),
			showCurrentAtPos = this._get( inst, "showCurrentAtPos" ),
			stepMonths = this._get( inst, "stepMonths" ),
			isMultiMonth = ( numMonths[ 0 ] !== 1 || numMonths[ 1 ] !== 1 ),
			currentDate = this._daylightSavingAdjust( ( !inst.currentDay ? new Date( 9999, 9, 9 ) :
				new Date( inst.currentYear, inst.currentMonth, inst.currentDay ) ) ),
			minDate = this._getMinMaxDate( inst, "min" ),
			maxDate = this._getMinMaxDate( inst, "max" ),
			drawMonth = inst.drawMonth - showCurrentAtPos,
			drawYear = inst.drawYear;

		if ( drawMonth < 0 ) {
			drawMonth += 12;
			drawYear--;
		}
		if ( maxDate ) {
			maxDraw = this._daylightSavingAdjust( new Date( maxDate.getFullYear(),
				maxDate.getMonth() - ( numMonths[ 0 ] * numMonths[ 1 ] ) + 1, maxDate.getDate() ) );
			maxDraw = ( minDate && maxDraw < minDate ? minDate : maxDraw );
			while ( this._daylightSavingAdjust( new Date( drawYear, drawMonth, 1 ) ) > maxDraw ) {
				drawMonth--;
				if ( drawMonth < 0 ) {
					drawMonth = 11;
					drawYear--;
				}
			}
		}
		inst.drawMonth = drawMonth;
		inst.drawYear = drawYear;

		prevText = this._get( inst, "prevText" );
		prevText = ( !navigationAsDateFormat ? prevText : this.formatDate( prevText,
			this._daylightSavingAdjust( new Date( drawYear, drawMonth - stepMonths, 1 ) ),
			this._getFormatConfig( inst ) ) );

		prev = ( this._canAdjustMonth( inst, -1, drawYear, drawMonth ) ?
			"<a class='ui-datepicker-prev ui-corner-all' data-handler='prev' data-event='click'" +
			" title='" + prevText + "'><span class='ui-icon ui-icon-circle-triangle-" + ( isRTL ? "e" : "w" ) + "'>" + prevText + "</span></a>" :
			( hideIfNoPrevNext ? "" : "<a class='ui-datepicker-prev ui-corner-all ui-state-disabled' title='" + prevText + "'><span class='ui-icon ui-icon-circle-triangle-" + ( isRTL ? "e" : "w" ) + "'>" + prevText + "</span></a>" ) );

		nextText = this._get( inst, "nextText" );
		nextText = ( !navigationAsDateFormat ? nextText : this.formatDate( nextText,
			this._daylightSavingAdjust( new Date( drawYear, drawMonth + stepMonths, 1 ) ),
			this._getFormatConfig( inst ) ) );

		next = ( this._canAdjustMonth( inst, +1, drawYear, drawMonth ) ?
			"<a class='ui-datepicker-next ui-corner-all' data-handler='next' data-event='click'" +
			" title='" + nextText + "'><span class='ui-icon ui-icon-circle-triangle-" + ( isRTL ? "w" : "e" ) + "'>" + nextText + "</span></a>" :
			( hideIfNoPrevNext ? "" : "<a class='ui-datepicker-next ui-corner-all ui-state-disabled' title='" + nextText + "'><span class='ui-icon ui-icon-circle-triangle-" + ( isRTL ? "w" : "e" ) + "'>" + nextText + "</span></a>" ) );

		currentText = this._get( inst, "currentText" );
		gotoDate = ( this._get( inst, "gotoCurrent" ) && inst.currentDay ? currentDate : today );
		currentText = ( !navigationAsDateFormat ? currentText :
			this.formatDate( currentText, gotoDate, this._getFormatConfig( inst ) ) );

		controls = ( !inst.inline ? "<button type='button' class='ui-datepicker-close ui-state-default ui-priority-primary ui-corner-all' data-handler='hide' data-event='click'>" +
			this._get( inst, "closeText" ) + "</button>" : "" );

		buttonPanel = ( showButtonPanel ) ? "<div class='ui-datepicker-buttonpane ui-widget-content'>" + ( isRTL ? controls : "" ) +
			( this._isInRange( inst, gotoDate ) ? "<button type='button' class='ui-datepicker-current ui-state-default ui-priority-secondary ui-corner-all' data-handler='today' data-event='click'" +
			">" + currentText + "</button>" : "" ) + ( isRTL ? "" : controls ) + "</div>" : "";

		firstDay = parseInt( this._get( inst, "firstDay" ), 10 );
		firstDay = ( isNaN( firstDay ) ? 0 : firstDay );

		showWeek = this._get( inst, "showWeek" );
		dayNames = this._get( inst, "dayNames" );
		dayNamesMin = this._get( inst, "dayNamesMin" );
		monthNames = this._get( inst, "monthNames" );
		monthNamesShort = this._get( inst, "monthNamesShort" );
		beforeShowDay = this._get( inst, "beforeShowDay" );
		showOtherMonths = this._get( inst, "showOtherMonths" );
		selectOtherMonths = this._get( inst, "selectOtherMonths" );
		defaultDate = this._getDefaultDate( inst );
		html = "";

		for ( row = 0; row < numMonths[ 0 ]; row++ ) {
			group = "";
			this.maxRows = 4;
			for ( col = 0; col < numMonths[ 1 ]; col++ ) {
				selectedDate = this._daylightSavingAdjust( new Date( drawYear, drawMonth, inst.selectedDay ) );
				cornerClass = " ui-corner-all";
				calender = "";
				if ( isMultiMonth ) {
					calender += "<div class='ui-datepicker-group";
					if ( numMonths[ 1 ] > 1 ) {
						switch ( col ) {
							case 0: calender += " ui-datepicker-group-first";
								cornerClass = " ui-corner-" + ( isRTL ? "right" : "left" ); break;
							case numMonths[ 1 ] - 1: calender += " ui-datepicker-group-last";
								cornerClass = " ui-corner-" + ( isRTL ? "left" : "right" ); break;
							default: calender += " ui-datepicker-group-middle"; cornerClass = ""; break;
						}
					}
					calender += "'>";
				}
				calender += "<div class='ui-datepicker-header ui-widget-header ui-helper-clearfix" + cornerClass + "'>" +
					( /all|left/.test( cornerClass ) && row === 0 ? ( isRTL ? next : prev ) : "" ) +
					( /all|right/.test( cornerClass ) && row === 0 ? ( isRTL ? prev : next ) : "" ) +
					this._generateMonthYearHeader( inst, drawMonth, drawYear, minDate, maxDate,
					row > 0 || col > 0, monthNames, monthNamesShort ) + // draw
																		// month
																		// headers
					"</div><table class='ui-datepicker-calendar'><thead>" +
					"<tr>";
				thead = ( showWeek ? "<th class='ui-datepicker-week-col'>" + this._get( inst, "weekHeader" ) + "</th>" : "" );
				for ( dow = 0; dow < 7; dow++ ) { // days of the week
					day = ( dow + firstDay ) % 7;
					thead += "<th scope='col'" + ( ( dow + firstDay + 6 ) % 7 >= 5 ? " class='ui-datepicker-week-end'" : "" ) + ">" +
						"<span title='" + dayNames[ day ] + "'>" + dayNamesMin[ day ] + "</span></th>";
				}
				calender += thead + "</tr></thead><tbody>";
				daysInMonth = this._getDaysInMonth( drawYear, drawMonth );
				if ( drawYear === inst.selectedYear && drawMonth === inst.selectedMonth ) {
					inst.selectedDay = Math.min( inst.selectedDay, daysInMonth );
				}
				leadDays = ( this._getFirstDayOfMonth( drawYear, drawMonth ) - firstDay + 7 ) % 7;
				curRows = Math.ceil( ( leadDays + daysInMonth ) / 7 ); // calculate
																		// the
																		// number
																		// of
																		// rows
																		// to
																		// generate
				numRows = ( isMultiMonth ? this.maxRows > curRows ? this.maxRows : curRows : curRows ); // If
																										// multiple
																										// months,
																										// use
																										// the
																										// higher
																										// number
																										// of
																										// rows
																										// (see
																										// #7043)
				this.maxRows = numRows;
				printDate = this._daylightSavingAdjust( new Date( drawYear, drawMonth, 1 - leadDays ) );
				for ( dRow = 0; dRow < numRows; dRow++ ) { // create date
															// picker rows
					calender += "<tr>";
					tbody = ( !showWeek ? "" : "<td class='ui-datepicker-week-col'>" +
						this._get( inst, "calculateWeek" )( printDate ) + "</td>" );
					for ( dow = 0; dow < 7; dow++ ) { // create date picker
														// days
						daySettings = ( beforeShowDay ?
							beforeShowDay.apply( ( inst.input ? inst.input[ 0 ] : null ), [ printDate ] ) : [ true, "" ] );
						otherMonth = ( printDate.getMonth() !== drawMonth );
						unselectable = ( otherMonth && !selectOtherMonths ) || !daySettings[ 0 ] ||
							( minDate && printDate < minDate ) || ( maxDate && printDate > maxDate );
						tbody += "<td class='" +
							( ( dow + firstDay + 6 ) % 7 >= 5 ? " ui-datepicker-week-end" : "" ) + // highlight
																									// weekends
							( otherMonth ? " ui-datepicker-other-month" : "" ) + // highlight
																					// days
																					// from
																					// other
																					// months
							( ( printDate.getTime() === selectedDate.getTime() && drawMonth === inst.selectedMonth && inst._keyEvent ) || // user
																																			// pressed
																																			// key
							( defaultDate.getTime() === printDate.getTime() && defaultDate.getTime() === selectedDate.getTime() ) ?

							// or defaultDate is current printedDate and
							// defaultDate is selectedDate
							" " + this._dayOverClass : "" ) + // highlight
																// selected day
							( unselectable ? " " + this._unselectableClass + " ui-state-disabled" : "" ) +  // highlight
																											// unselectable
																											// days
							( otherMonth && !showOtherMonths ? "" : " " + daySettings[ 1 ] + // highlight
																								// custom
																								// dates
							( printDate.getTime() === currentDate.getTime() ? " " + this._currentClass : "" ) + // highlight
																												// selected
																												// day
							( printDate.getTime() === today.getTime() ? " ui-datepicker-today" : "" ) ) + "'" + // highlight
																												// today
																												// (if
																												// different)
							( ( !otherMonth || showOtherMonths ) && daySettings[ 2 ] ? " title='" + daySettings[ 2 ].replace( /'/g, "&#39;" ) + "'" : "" ) + // cell
																																								// title
							( unselectable ? "" : " data-handler='selectDay' data-event='click' data-month='" + printDate.getMonth() + "' data-year='" + printDate.getFullYear() + "'" ) + ">" + // actions
							( otherMonth && !showOtherMonths ? "&#xa0;" : // display
																			// for
																			// other
																			// months
							( unselectable ? "<span class='ui-state-default'>" + printDate.getDate() + "</span>" : "<a class='ui-state-default" +
							( printDate.getTime() === today.getTime() ? " ui-state-highlight" : "" ) +
							( printDate.getTime() === currentDate.getTime() ? " ui-state-active" : "" ) + // highlight
																											// selected
																											// day
							( otherMonth ? " ui-priority-secondary" : "" ) + // distinguish
																				// dates
																				// from
																				// other
																				// months
							"' href='#'>" + printDate.getDate() + "</a>" ) ) + "</td>"; // display
																						// selectable
																						// date
						printDate.setDate( printDate.getDate() + 1 );
						printDate = this._daylightSavingAdjust( printDate );
					}
					calender += tbody + "</tr>";
				}
				drawMonth++;
				if ( drawMonth > 11 ) {
					drawMonth = 0;
					drawYear++;
				}
				calender += "</tbody></table>" + ( isMultiMonth ? "</div>" +
							( ( numMonths[ 0 ] > 0 && col === numMonths[ 1 ] - 1 ) ? "<div class='ui-datepicker-row-break'></div>" : "" ) : "" );
				group += calender;
			}
			html += group;
		}
		html += buttonPanel;
		inst._keyEvent = false;
		return html;
	},

	/* Generate the month and year header. */
	_generateMonthYearHeader: function( inst, drawMonth, drawYear, minDate, maxDate,
			secondary, monthNames, monthNamesShort ) {

		var inMinYear, inMaxYear, month, years, thisYear, determineYear, year, endYear,
			changeMonth = this._get( inst, "changeMonth" ),
			changeYear = this._get( inst, "changeYear" ),
			showMonthAfterYear = this._get( inst, "showMonthAfterYear" ),
			html = "<div class='ui-datepicker-title'>",
			monthHtml = "";

		// Month selection
		if ( secondary || !changeMonth ) {
			monthHtml += "<span class='ui-datepicker-month'>" + monthNames[ drawMonth ] + "</span>";
		} else {
			inMinYear = ( minDate && minDate.getFullYear() === drawYear );
			inMaxYear = ( maxDate && maxDate.getFullYear() === drawYear );
			monthHtml += "<select class='ui-datepicker-month' data-handler='selectMonth' data-event='change'>";
			for ( month = 0; month < 12; month++ ) {
				if ( ( !inMinYear || month >= minDate.getMonth() ) && ( !inMaxYear || month <= maxDate.getMonth() ) ) {
					monthHtml += "<option value='" + month + "'" +
						( month === drawMonth ? " selected='selected'" : "" ) +
						">" + monthNamesShort[ month ] + "</option>";
				}
			}
			monthHtml += "</select>";
		}

		if ( !showMonthAfterYear ) {
			html += monthHtml + ( secondary || !( changeMonth && changeYear ) ? "&#xa0;" : "" );
		}

		// Year selection
		if ( !inst.yearshtml ) {
			inst.yearshtml = "";
			if ( secondary || !changeYear ) {
				html += "<span class='ui-datepicker-year'>" + drawYear + "</span>";
			} else {

				// determine range of years to display
				years = this._get( inst, "yearRange" ).split( ":" );
				thisYear = new Date().getFullYear();
				determineYear = function( value ) {
					var year = ( value.match( /c[+\-].*/ ) ? drawYear + parseInt( value.substring( 1 ), 10 ) :
						( value.match( /[+\-].*/ ) ? thisYear + parseInt( value, 10 ) :
						parseInt( value, 10 ) ) );
					return ( isNaN( year ) ? thisYear : year );
				};
				year = determineYear( years[ 0 ] );
				endYear = Math.max( year, determineYear( years[ 1 ] || "" ) );
				year = ( minDate ? Math.max( year, minDate.getFullYear() ) : year );
				endYear = ( maxDate ? Math.min( endYear, maxDate.getFullYear() ) : endYear );
				inst.yearshtml += "<select class='ui-datepicker-year' data-handler='selectYear' data-event='change'>";
				for ( ; year <= endYear; year++ ) {
					inst.yearshtml += "<option value='" + year + "'" +
						( year === drawYear ? " selected='selected'" : "" ) +
						">" + year + "</option>";
				}
				inst.yearshtml += "</select>";

				html += inst.yearshtml;
				inst.yearshtml = null;
			}
		}

		html += this._get( inst, "yearSuffix" );
		if ( showMonthAfterYear ) {
			html += ( secondary || !( changeMonth && changeYear ) ? "&#xa0;" : "" ) + monthHtml;
		}
		html += "</div>"; // Close datepicker_header
		return html;
	},

	/* Adjust one of the date sub-fields. */
	_adjustInstDate: function( inst, offset, period ) {
		var year = inst.selectedYear + ( period === "Y" ? offset : 0 ),
			month = inst.selectedMonth + ( period === "M" ? offset : 0 ),
			day = Math.min( inst.selectedDay, this._getDaysInMonth( year, month ) ) + ( period === "D" ? offset : 0 ),
			date = this._restrictMinMax( inst, this._daylightSavingAdjust( new Date( year, month, day ) ) );

		inst.selectedDay = date.getDate();
		inst.drawMonth = inst.selectedMonth = date.getMonth();
		inst.drawYear = inst.selectedYear = date.getFullYear();
		if ( period === "M" || period === "Y" ) {
			this._notifyChange( inst );
		}
	},

	/* Ensure a date is within any min/max bounds. */
	_restrictMinMax: function( inst, date ) {
		var minDate = this._getMinMaxDate( inst, "min" ),
			maxDate = this._getMinMaxDate( inst, "max" ),
			newDate = ( minDate && date < minDate ? minDate : date );
		return ( maxDate && newDate > maxDate ? maxDate : newDate );
	},

	/* Notify change of month/year. */
	_notifyChange: function( inst ) {
		var onChange = this._get( inst, "onChangeMonthYear" );
		if ( onChange ) {
			onChange.apply( ( inst.input ? inst.input[ 0 ] : null ),
				[ inst.selectedYear, inst.selectedMonth + 1, inst ] );
		}
	},

	/* Determine the number of months to show. */
	_getNumberOfMonths: function( inst ) {
		var numMonths = this._get( inst, "numberOfMonths" );
		return ( numMonths == null ? [ 1, 1 ] : ( typeof numMonths === "number" ? [ 1, numMonths ] : numMonths ) );
	},

	/* Determine the current maximum date - ensure no time components are set. */
	_getMinMaxDate: function( inst, minMax ) {
		return this._determineDate( inst, this._get( inst, minMax + "Date" ), null );
	},

	/* Find the number of days in a given month. */
	_getDaysInMonth: function( year, month ) {
		return 32 - this._daylightSavingAdjust( new Date( year, month, 32 ) ).getDate();
	},

	/* Find the day of the week of the first of a month. */
	_getFirstDayOfMonth: function( year, month ) {
		return new Date( year, month, 1 ).getDay();
	},

	/* Determines if we should allow a "next/prev" month display change. */
	_canAdjustMonth: function( inst, offset, curYear, curMonth ) {
		var numMonths = this._getNumberOfMonths( inst ),
			date = this._daylightSavingAdjust( new Date( curYear,
			curMonth + ( offset < 0 ? offset : numMonths[ 0 ] * numMonths[ 1 ] ), 1 ) );

		if ( offset < 0 ) {
			date.setDate( this._getDaysInMonth( date.getFullYear(), date.getMonth() ) );
		}
		return this._isInRange( inst, date );
	},

	/* Is the given date in the accepted range? */
	_isInRange: function( inst, date ) {
		var yearSplit, currentYear,
			minDate = this._getMinMaxDate( inst, "min" ),
			maxDate = this._getMinMaxDate( inst, "max" ),
			minYear = null,
			maxYear = null,
			years = this._get( inst, "yearRange" );
			if ( years ) {
				yearSplit = years.split( ":" );
				currentYear = new Date().getFullYear();
				minYear = parseInt( yearSplit[ 0 ], 10 );
				maxYear = parseInt( yearSplit[ 1 ], 10 );
				if ( yearSplit[ 0 ].match( /[+\-].*/ ) ) {
					minYear += currentYear;
				}
				if ( yearSplit[ 1 ].match( /[+\-].*/ ) ) {
					maxYear += currentYear;
				}
			}

		return ( ( !minDate || date.getTime() >= minDate.getTime() ) &&
			( !maxDate || date.getTime() <= maxDate.getTime() ) &&
			( !minYear || date.getFullYear() >= minYear ) &&
			( !maxYear || date.getFullYear() <= maxYear ) );
	},

	/* Provide the configuration settings for formatting/parsing. */
	_getFormatConfig: function( inst ) {
		var shortYearCutoff = this._get( inst, "shortYearCutoff" );
		shortYearCutoff = ( typeof shortYearCutoff !== "string" ? shortYearCutoff :
			new Date().getFullYear() % 100 + parseInt( shortYearCutoff, 10 ) );
		return { shortYearCutoff: shortYearCutoff,
			dayNamesShort: this._get( inst, "dayNamesShort" ), dayNames: this._get( inst, "dayNames" ),
			monthNamesShort: this._get( inst, "monthNamesShort" ), monthNames: this._get( inst, "monthNames" ) };
	},

	/* Format the given date for display. */
	_formatDate: function( inst, day, month, year ) {
		if ( !day ) {
			inst.currentDay = inst.selectedDay;
			inst.currentMonth = inst.selectedMonth;
			inst.currentYear = inst.selectedYear;
		}
		var date = ( day ? ( typeof day === "object" ? day :
			this._daylightSavingAdjust( new Date( year, month, day ) ) ) :
			this._daylightSavingAdjust( new Date( inst.currentYear, inst.currentMonth, inst.currentDay ) ) );
		return this.formatDate( this._get( inst, "dateFormat" ), date, this._getFormatConfig( inst ) );
	}
} );

/*
 * Bind hover events for datepicker elements. Done via delegate so the binding
 * only occurs once in the lifetime of the parent div. Global
 * datepicker_instActive, set by _updateDatepicker allows the handlers to find
 * their way back to the active picker.
 */
function datepicker_bindHover( dpDiv ) {
	var selector = "button, .ui-datepicker-prev, .ui-datepicker-next, .ui-datepicker-calendar td a";
	return dpDiv.on( "mouseout", selector, function() {
			$( this ).removeClass( "ui-state-hover" );
			if ( this.className.indexOf( "ui-datepicker-prev" ) !== -1 ) {
				$( this ).removeClass( "ui-datepicker-prev-hover" );
			}
			if ( this.className.indexOf( "ui-datepicker-next" ) !== -1 ) {
				$( this ).removeClass( "ui-datepicker-next-hover" );
			}
		} )
		.on( "mouseover", selector, datepicker_handleMouseover );
}

function datepicker_handleMouseover() {
	if ( !$.datepicker._isDisabledDatepicker( datepicker_instActive.inline ? datepicker_instActive.dpDiv.parent()[ 0 ] : datepicker_instActive.input[ 0 ] ) ) {
		$( this ).parents( ".ui-datepicker-calendar" ).find( "a" ).removeClass( "ui-state-hover" );
		$( this ).addClass( "ui-state-hover" );
		if ( this.className.indexOf( "ui-datepicker-prev" ) !== -1 ) {
			$( this ).addClass( "ui-datepicker-prev-hover" );
		}
		if ( this.className.indexOf( "ui-datepicker-next" ) !== -1 ) {
			$( this ).addClass( "ui-datepicker-next-hover" );
		}
	}
}

/* jQuery extend now ignores nulls! */
function datepicker_extendRemove( target, props ) {
	$.extend( target, props );
	for ( var name in props ) {
		if ( props[ name ] == null ) {
			target[ name ] = props[ name ];
		}
	}
	return target;
}

/*
 * Invoke the datepicker functionality. @param options string - a command,
 * optionally followed by additional parameters or Object - settings for
 * attaching new datepicker functionality @return jQuery object
 */
$.fn.datepicker = function( options ) {

	/* Verify an empty collection wasn't passed - Fixes #6976 */
	if ( !this.length ) {
		return this;
	}

	/* Initialise the date picker. */
	if ( !$.datepicker.initialized ) {
		$( document ).on( "mousedown", $.datepicker._checkExternalClick );
		$.datepicker.initialized = true;
	}

	/* Append datepicker main container to body if not exist. */
	if ( $( "#" + $.datepicker._mainDivId ).length === 0 ) {
		$( "body" ).append( $.datepicker.dpDiv );
	}

	var otherArgs = Array.prototype.slice.call( arguments, 1 );
	if ( typeof options === "string" && ( options === "isDisabled" || options === "getDate" || options === "widget" ) ) {
		return $.datepicker[ "_" + options + "Datepicker" ].
			apply( $.datepicker, [ this[ 0 ] ].concat( otherArgs ) );
	}
	if ( options === "option" && arguments.length === 2 && typeof arguments[ 1 ] === "string" ) {
		return $.datepicker[ "_" + options + "Datepicker" ].
			apply( $.datepicker, [ this[ 0 ] ].concat( otherArgs ) );
	}
	return this.each( function() {
		typeof options === "string" ?
			$.datepicker[ "_" + options + "Datepicker" ].
				apply( $.datepicker, [ this ].concat( otherArgs ) ) :
			$.datepicker._attachDatepicker( this, options );
	} );
};

$.datepicker = new Datepicker(); // singleton instance
$.datepicker.initialized = false;
$.datepicker.uuid = new Date().getTime();
$.datepicker.version = "1.12.1";

var widgetsDatepicker = $.datepicker;


/*
 * ! jQuery UI Slider 1.12.1 http://jqueryui.com
 * 
 * Copyright jQuery Foundation and other contributors Released under the MIT
 * license. http://jquery.org/license
 */

// >>label: Slider
// >>group: Widgets
// >>description: Displays a flexible slider with ranges and accessibility via
// keyboard.
// >>docs: http://api.jqueryui.com/slider/
// >>demos: http://jqueryui.com/slider/
// >>css.structure: ../../themes/base/core.css
// >>css.structure: ../../themes/base/slider.css
// >>css.theme: ../../themes/base/theme.css



var widgetsSlider = $.widget( "ui.slider", $.ui.mouse, {
	version: "1.12.1",
	widgetEventPrefix: "slide",

	options: {
		animate: false,
		classes: {
			"ui-slider": "ui-corner-all",
			"ui-slider-handle": "ui-corner-all",

			// Note: ui-widget-header isn't the most fittingly semantic
			// framework class for this
			// element, but worked best visually with a variety of themes
			"ui-slider-range": "ui-corner-all ui-widget-header"
		},
		distance: 0,
		max: 100,
		min: 0,
		orientation: "horizontal",
		range: false,
		step: 1,
		value: 0,
		values: null,

		// Callbacks
		change: null,
		slide: null,
		start: null,
		stop: null
	},

	// Number of pages in a slider
	// (how many times can you page up/down to go through the whole range)
	numPages: 5,

	_create: function() {
		this._keySliding = false;
		this._mouseSliding = false;
		this._animateOff = true;
		this._handleIndex = null;
		this._detectOrientation();
		this._mouseInit();
		this._calculateNewMax();

		this._addClass( "ui-slider ui-slider-" + this.orientation,
			"ui-widget ui-widget-content" );

		this._refresh();

		this._animateOff = false;
	},

	_refresh: function() {
		this._createRange();
		this._createHandles();
		this._setupEvents();
		this._refreshValue();
	},

	_createHandles: function() {
		var i, handleCount,
			options = this.options,
			existingHandles = this.element.find( ".ui-slider-handle" ),
			handle = "<span tabindex='0'></span>",
			handles = [];

		handleCount = ( options.values && options.values.length ) || 1;

		if ( existingHandles.length > handleCount ) {
			existingHandles.slice( handleCount ).remove();
			existingHandles = existingHandles.slice( 0, handleCount );
		}

		for ( i = existingHandles.length; i < handleCount; i++ ) {
			handles.push( handle );
		}

		this.handles = existingHandles.add( $( handles.join( "" ) ).appendTo( this.element ) );

		this._addClass( this.handles, "ui-slider-handle", "ui-state-default" );

		this.handle = this.handles.eq( 0 );

		this.handles.each( function( i ) {
			$( this )
				.data( "ui-slider-handle-index", i )
				.attr( "tabIndex", 0 );
		} );
	},

	_createRange: function() {
		var options = this.options;

		if ( options.range ) {
			if ( options.range === true ) {
				if ( !options.values ) {
					options.values = [ this._valueMin(), this._valueMin() ];
				} else if ( options.values.length && options.values.length !== 2 ) {
					options.values = [ options.values[ 0 ], options.values[ 0 ] ];
				} else if ( $.isArray( options.values ) ) {
					options.values = options.values.slice( 0 );
				}
			}

			if ( !this.range || !this.range.length ) {
				this.range = $( "<div>" )
					.appendTo( this.element );

				this._addClass( this.range, "ui-slider-range" );
			} else {
				this._removeClass( this.range, "ui-slider-range-min ui-slider-range-max" );

				// Handle range switching from true to min/max
				this.range.css( {
					"left": "",
					"bottom": ""
				} );
			}
			if ( options.range === "min" || options.range === "max" ) {
				this._addClass( this.range, "ui-slider-range-" + options.range );
			}
		} else {
			if ( this.range ) {
				this.range.remove();
			}
			this.range = null;
		}
	},

	_setupEvents: function() {
		this._off( this.handles );
		this._on( this.handles, this._handleEvents );
		this._hoverable( this.handles );
		this._focusable( this.handles );
	},

	_destroy: function() {
		this.handles.remove();
		if ( this.range ) {
			this.range.remove();
		}

		this._mouseDestroy();
	},

	_mouseCapture: function( event ) {
		var position, normValue, distance, closestHandle, index, allowed, offset, mouseOverHandle,
			that = this,
			o = this.options;

		if ( o.disabled ) {
			return false;
		}

		this.elementSize = {
			width: this.element.outerWidth(),
			height: this.element.outerHeight()
		};
		this.elementOffset = this.element.offset();

		position = { x: event.pageX, y: event.pageY };
		normValue = this._normValueFromMouse( position );
		distance = this._valueMax() - this._valueMin() + 1;
		this.handles.each( function( i ) {
			var thisDistance = Math.abs( normValue - that.values( i ) );
			if ( ( distance > thisDistance ) ||
				( distance === thisDistance &&
					( i === that._lastChangedValue || that.values( i ) === o.min ) ) ) {
				distance = thisDistance;
				closestHandle = $( this );
				index = i;
			}
		} );

		allowed = this._start( event, index );
		if ( allowed === false ) {
			return false;
		}
		this._mouseSliding = true;

		this._handleIndex = index;

		this._addClass( closestHandle, null, "ui-state-active" );
		closestHandle.trigger( "focus" );

		offset = closestHandle.offset();
		mouseOverHandle = !$( event.target ).parents().addBack().is( ".ui-slider-handle" );
		this._clickOffset = mouseOverHandle ? { left: 0, top: 0 } : {
			left: event.pageX - offset.left - ( closestHandle.width() / 2 ),
			top: event.pageY - offset.top -
				( closestHandle.height() / 2 ) -
				( parseInt( closestHandle.css( "borderTopWidth" ), 10 ) || 0 ) -
				( parseInt( closestHandle.css( "borderBottomWidth" ), 10 ) || 0 ) +
				( parseInt( closestHandle.css( "marginTop" ), 10 ) || 0 )
		};

		if ( !this.handles.hasClass( "ui-state-hover" ) ) {
			this._slide( event, index, normValue );
		}
		this._animateOff = true;
		return true;
	},

	_mouseStart: function() {
		return true;
	},

	_mouseDrag: function( event ) {
		var position = { x: event.pageX, y: event.pageY },
			normValue = this._normValueFromMouse( position );

		this._slide( event, this._handleIndex, normValue );

		return false;
	},

	_mouseStop: function( event ) {
		this._removeClass( this.handles, null, "ui-state-active" );
		this._mouseSliding = false;

		this._stop( event, this._handleIndex );
		this._change( event, this._handleIndex );

		this._handleIndex = null;
		this._clickOffset = null;
		this._animateOff = false;

		return false;
	},

	_detectOrientation: function() {
		this.orientation = ( this.options.orientation === "vertical" ) ? "vertical" : "horizontal";
	},

	_normValueFromMouse: function( position ) {
		var pixelTotal,
			pixelMouse,
			percentMouse,
			valueTotal,
			valueMouse;

		if ( this.orientation === "horizontal" ) {
			pixelTotal = this.elementSize.width;
			pixelMouse = position.x - this.elementOffset.left -
				( this._clickOffset ? this._clickOffset.left : 0 );
		} else {
			pixelTotal = this.elementSize.height;
			pixelMouse = position.y - this.elementOffset.top -
				( this._clickOffset ? this._clickOffset.top : 0 );
		}

		percentMouse = ( pixelMouse / pixelTotal );
		if ( percentMouse > 1 ) {
			percentMouse = 1;
		}
		if ( percentMouse < 0 ) {
			percentMouse = 0;
		}
		if ( this.orientation === "vertical" ) {
			percentMouse = 1 - percentMouse;
		}

		valueTotal = this._valueMax() - this._valueMin();
		valueMouse = this._valueMin() + percentMouse * valueTotal;

		return this._trimAlignValue( valueMouse );
	},

	_uiHash: function( index, value, values ) {
		var uiHash = {
			handle: this.handles[ index ],
			handleIndex: index,
			value: value !== undefined ? value : this.value()
		};

		if ( this._hasMultipleValues() ) {
			uiHash.value = value !== undefined ? value : this.values( index );
			uiHash.values = values || this.values();
		}

		return uiHash;
	},

	_hasMultipleValues: function() {
		return this.options.values && this.options.values.length;
	},

	_start: function( event, index ) {
		return this._trigger( "start", event, this._uiHash( index ) );
	},

	_slide: function( event, index, newVal ) {
		var allowed, otherVal,
			currentValue = this.value(),
			newValues = this.values();

		if ( this._hasMultipleValues() ) {
			otherVal = this.values( index ? 0 : 1 );
			currentValue = this.values( index );

			if ( this.options.values.length === 2 && this.options.range === true ) {
				newVal =  index === 0 ? Math.min( otherVal, newVal ) : Math.max( otherVal, newVal );
			}

			newValues[ index ] = newVal;
		}

		if ( newVal === currentValue ) {
			return;
		}

		allowed = this._trigger( "slide", event, this._uiHash( index, newVal, newValues ) );

		// A slide can be canceled by returning false from the slide callback
		if ( allowed === false ) {
			return;
		}

		if ( this._hasMultipleValues() ) {
			this.values( index, newVal );
		} else {
			this.value( newVal );
		}
	},

	_stop: function( event, index ) {
		this._trigger( "stop", event, this._uiHash( index ) );
	},

	_change: function( event, index ) {
		if ( !this._keySliding && !this._mouseSliding ) {

			// store the last changed value index for reference when handles
			// overlap
			this._lastChangedValue = index;
			this._trigger( "change", event, this._uiHash( index ) );
		}
	},

	value: function( newValue ) {
		if ( arguments.length ) {
			this.options.value = this._trimAlignValue( newValue );
			this._refreshValue();
			this._change( null, 0 );
			return;
		}

		return this._value();
	},

	values: function( index, newValue ) {
		var vals,
			newValues,
			i;

		if ( arguments.length > 1 ) {
			this.options.values[ index ] = this._trimAlignValue( newValue );
			this._refreshValue();
			this._change( null, index );
			return;
		}

		if ( arguments.length ) {
			if ( $.isArray( arguments[ 0 ] ) ) {
				vals = this.options.values;
				newValues = arguments[ 0 ];
				for ( i = 0; i < vals.length; i += 1 ) {
					vals[ i ] = this._trimAlignValue( newValues[ i ] );
					this._change( null, i );
				}
				this._refreshValue();
			} else {
				if ( this._hasMultipleValues() ) {
					return this._values( index );
				} else {
					return this.value();
				}
			}
		} else {
			return this._values();
		}
	},

	_setOption: function( key, value ) {
		var i,
			valsLength = 0;

		if ( key === "range" && this.options.range === true ) {
			if ( value === "min" ) {
				this.options.value = this._values( 0 );
				this.options.values = null;
			} else if ( value === "max" ) {
				this.options.value = this._values( this.options.values.length - 1 );
				this.options.values = null;
			}
		}

		if ( $.isArray( this.options.values ) ) {
			valsLength = this.options.values.length;
		}

		this._super( key, value );

		switch ( key ) {
			case "orientation":
				this._detectOrientation();
				this._removeClass( "ui-slider-horizontal ui-slider-vertical" )
					._addClass( "ui-slider-" + this.orientation );
				this._refreshValue();
				if ( this.options.range ) {
					this._refreshRange( value );
				}

				// Reset positioning from previous orientation
				this.handles.css( value === "horizontal" ? "bottom" : "left", "" );
				break;
			case "value":
				this._animateOff = true;
				this._refreshValue();
				this._change( null, 0 );
				this._animateOff = false;
				break;
			case "values":
				this._animateOff = true;
				this._refreshValue();

				// Start from the last handle to prevent unreachable handles
				// (#9046)
				for ( i = valsLength - 1; i >= 0; i-- ) {
					this._change( null, i );
				}
				this._animateOff = false;
				break;
			case "step":
			case "min":
			case "max":
				this._animateOff = true;
				this._calculateNewMax();
				this._refreshValue();
				this._animateOff = false;
				break;
			case "range":
				this._animateOff = true;
				this._refresh();
				this._animateOff = false;
				break;
		}
	},

	_setOptionDisabled: function( value ) {
		this._super( value );

		this._toggleClass( null, "ui-state-disabled", !!value );
	},

	// internal value getter
	// _value() returns value trimmed by min and max, aligned by step
	_value: function() {
		var val = this.options.value;
		val = this._trimAlignValue( val );

		return val;
	},

	// internal values getter
	// _values() returns array of values trimmed by min and max, aligned by step
	// _values( index ) returns single value trimmed by min and max, aligned by
	// step
	_values: function( index ) {
		var val,
			vals,
			i;

		if ( arguments.length ) {
			val = this.options.values[ index ];
			val = this._trimAlignValue( val );

			return val;
		} else if ( this._hasMultipleValues() ) {

			// .slice() creates a copy of the array
			// this copy gets trimmed by min and max and then returned
			vals = this.options.values.slice();
			for ( i = 0; i < vals.length; i += 1 ) {
				vals[ i ] = this._trimAlignValue( vals[ i ] );
			}

			return vals;
		} else {
			return [];
		}
	},

	// Returns the step-aligned value that val is closest to, between
	// (inclusive) min and max
	_trimAlignValue: function( val ) {
		if ( val <= this._valueMin() ) {
			return this._valueMin();
		}
		if ( val >= this._valueMax() ) {
			return this._valueMax();
		}
		var step = ( this.options.step > 0 ) ? this.options.step : 1,
			valModStep = ( val - this._valueMin() ) % step,
			alignValue = val - valModStep;

		if ( Math.abs( valModStep ) * 2 >= step ) {
			alignValue += ( valModStep > 0 ) ? step : ( -step );
		}

		// Since JavaScript has problems with large floats, round
		// the final value to 5 digits after the decimal point (see #4124)
		return parseFloat( alignValue.toFixed( 5 ) );
	},

	_calculateNewMax: function() {
		var max = this.options.max,
			min = this._valueMin(),
			step = this.options.step,
			aboveMin = Math.round( ( max - min ) / step ) * step;
		max = aboveMin + min;
		if ( max > this.options.max ) {

			// If max is not divisible by step, rounding off may increase its
			// value
			max -= step;
		}
		this.max = parseFloat( max.toFixed( this._precision() ) );
	},

	_precision: function() {
		var precision = this._precisionOf( this.options.step );
		if ( this.options.min !== null ) {
			precision = Math.max( precision, this._precisionOf( this.options.min ) );
		}
		return precision;
	},

	_precisionOf: function( num ) {
		var str = num.toString(),
			decimal = str.indexOf( "." );
		return decimal === -1 ? 0 : str.length - decimal - 1;
	},

	_valueMin: function() {
		return this.options.min;
	},

	_valueMax: function() {
		return this.max;
	},

	_refreshRange: function( orientation ) {
		if ( orientation === "vertical" ) {
			this.range.css( { "width": "", "left": "" } );
		}
		if ( orientation === "horizontal" ) {
			this.range.css( { "height": "", "bottom": "" } );
		}
	},

	_refreshValue: function() {
		var lastValPercent, valPercent, value, valueMin, valueMax,
			oRange = this.options.range,
			o = this.options,
			that = this,
			animate = ( !this._animateOff ) ? o.animate : false,
			_set = {};

		if ( this._hasMultipleValues() ) {
			this.handles.each( function( i ) {
				valPercent = ( that.values( i ) - that._valueMin() ) / ( that._valueMax() -
					that._valueMin() ) * 100;
				_set[ that.orientation === "horizontal" ? "left" : "bottom" ] = valPercent + "%";
				$( this ).stop( 1, 1 )[ animate ? "animate" : "css" ]( _set, o.animate );
				if ( that.options.range === true ) {
					if ( that.orientation === "horizontal" ) {
						if ( i === 0 ) {
							that.range.stop( 1, 1 )[ animate ? "animate" : "css" ]( {
								left: valPercent + "%"
							}, o.animate );
						}
						if ( i === 1 ) {
							that.range[ animate ? "animate" : "css" ]( {
								width: ( valPercent - lastValPercent ) + "%"
							}, {
								queue: false,
								duration: o.animate
							} );
						}
					} else {
						if ( i === 0 ) {
							that.range.stop( 1, 1 )[ animate ? "animate" : "css" ]( {
								bottom: ( valPercent ) + "%"
							}, o.animate );
						}
						if ( i === 1 ) {
							that.range[ animate ? "animate" : "css" ]( {
								height: ( valPercent - lastValPercent ) + "%"
							}, {
								queue: false,
								duration: o.animate
							} );
						}
					}
				}
				lastValPercent = valPercent;
			} );
		} else {
			value = this.value();
			valueMin = this._valueMin();
			valueMax = this._valueMax();
			valPercent = ( valueMax !== valueMin ) ?
					( value - valueMin ) / ( valueMax - valueMin ) * 100 :
					0;
			_set[ this.orientation === "horizontal" ? "left" : "bottom" ] = valPercent + "%";
			this.handle.stop( 1, 1 )[ animate ? "animate" : "css" ]( _set, o.animate );

			if ( oRange === "min" && this.orientation === "horizontal" ) {
				this.range.stop( 1, 1 )[ animate ? "animate" : "css" ]( {
					width: valPercent + "%"
				}, o.animate );
			}
			if ( oRange === "max" && this.orientation === "horizontal" ) {
				this.range.stop( 1, 1 )[ animate ? "animate" : "css" ]( {
					width: ( 100 - valPercent ) + "%"
				}, o.animate );
			}
			if ( oRange === "min" && this.orientation === "vertical" ) {
				this.range.stop( 1, 1 )[ animate ? "animate" : "css" ]( {
					height: valPercent + "%"
				}, o.animate );
			}
			if ( oRange === "max" && this.orientation === "vertical" ) {
				this.range.stop( 1, 1 )[ animate ? "animate" : "css" ]( {
					height: ( 100 - valPercent ) + "%"
				}, o.animate );
			}
		}
	},

	_handleEvents: {
		keydown: function( event ) {
			var allowed, curVal, newVal, step,
				index = $( event.target ).data( "ui-slider-handle-index" );

			switch ( event.keyCode ) {
				case $.ui.keyCode.HOME:
				case $.ui.keyCode.END:
				case $.ui.keyCode.PAGE_UP:
				case $.ui.keyCode.PAGE_DOWN:
				case $.ui.keyCode.UP:
				case $.ui.keyCode.RIGHT:
				case $.ui.keyCode.DOWN:
				case $.ui.keyCode.LEFT:
					event.preventDefault();
					if ( !this._keySliding ) {
						this._keySliding = true;
						this._addClass( $( event.target ), null, "ui-state-active" );
						allowed = this._start( event, index );
						if ( allowed === false ) {
							return;
						}
					}
					break;
			}

			step = this.options.step;
			if ( this._hasMultipleValues() ) {
				curVal = newVal = this.values( index );
			} else {
				curVal = newVal = this.value();
			}

			switch ( event.keyCode ) {
				case $.ui.keyCode.HOME:
					newVal = this._valueMin();
					break;
				case $.ui.keyCode.END:
					newVal = this._valueMax();
					break;
				case $.ui.keyCode.PAGE_UP:
					newVal = this._trimAlignValue(
						curVal + ( ( this._valueMax() - this._valueMin() ) / this.numPages )
					);
					break;
				case $.ui.keyCode.PAGE_DOWN:
					newVal = this._trimAlignValue(
						curVal - ( ( this._valueMax() - this._valueMin() ) / this.numPages ) );
					break;
				case $.ui.keyCode.UP:
				case $.ui.keyCode.RIGHT:
					if ( curVal === this._valueMax() ) {
						return;
					}
					newVal = this._trimAlignValue( curVal + step );
					break;
				case $.ui.keyCode.DOWN:
				case $.ui.keyCode.LEFT:
					if ( curVal === this._valueMin() ) {
						return;
					}
					newVal = this._trimAlignValue( curVal - step );
					break;
			}

			this._slide( event, index, newVal );
		},
		keyup: function( event ) {
			var index = $( event.target ).data( "ui-slider-handle-index" );

			if ( this._keySliding ) {
				this._keySliding = false;
				this._stop( event, index );
				this._change( event, index );
				this._removeClass( $( event.target ), null, "ui-state-active" );
			}
		}
	}
} );




}));
// Generated by CoffeeScript 1.4.0
/*
 * # # Opentip v2.4.6 # # More info at [www.opentip.org](http://www.opentip.org) # #
 * Copyright (c) 2012, Matias Meno # Graphics by Tjandra Mayerhold # #
 * Permission is hereby granted, free of charge, to any person obtaining a copy #
 * of this software and associated documentation files (the "Software"), to deal #
 * in the Software without restriction, including without limitation the rights #
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell #
 * copies of the Software, and to permit persons to whom the Software is #
 * furnished to do so, subject to the following conditions: # # The above
 * copyright notice and this permission notice shall be included in # all copies
 * or substantial portions of the Software. # # THE SOFTWARE IS PROVIDED "AS
 * IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR # IMPLIED, INCLUDING BUT NOT
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, # FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE # AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER # LIABILITY, WHETHER IN AN
 * ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, # OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN # THE SOFTWARE. #
 */

var Opentip, firstAdapter, i, mouseMoved, mousePosition, mousePositionObservers, position, vendors, _i, _len, _ref,
  __slice = [].slice,
  __indexOf = [].indexOf || function(item) { for (var i = 0, l = this.length; i < l; i++) { if (i in this && this[i] === item) return i; } return -1; },
  __hasProp = {}.hasOwnProperty;

Opentip = (function() {

  Opentip.prototype.STICKS_OUT_TOP = 1;

  Opentip.prototype.STICKS_OUT_BOTTOM = 2;

  Opentip.prototype.STICKS_OUT_LEFT = 1;

  Opentip.prototype.STICKS_OUT_RIGHT = 2;

  Opentip.prototype["class"] = {
    container: "opentip-container",
    opentip: "opentip",
    header: "ot-header",
    content: "ot-content",
    loadingIndicator: "ot-loading-indicator",
    close: "ot-close",
    goingToHide: "ot-going-to-hide",
    hidden: "ot-hidden",
    hiding: "ot-hiding",
    goingToShow: "ot-going-to-show",
    showing: "ot-showing",
    visible: "ot-visible",
    loading: "ot-loading",
    ajaxError: "ot-ajax-error",
    fixed: "ot-fixed",
    showEffectPrefix: "ot-show-effect-",
    hideEffectPrefix: "ot-hide-effect-",
    stylePrefix: "style-"
  };

  function Opentip(element, content, title, options) {
    var elementsOpentips, hideTrigger, methodToBind, optionSources, prop, styleName, _i, _j, _len, _len1, _ref, _ref1, _ref2, _tmpStyle,
      _this = this;
    this.id = ++Opentip.lastId;
    this.debug("Creating Opentip.");
    Opentip.tips.push(this);
    this.adapter = Opentip.adapter;
    elementsOpentips = this.adapter.data(element, "opentips") || [];
    elementsOpentips.push(this);
    this.adapter.data(element, "opentips", elementsOpentips);
    this.triggerElement = this.adapter.wrap(element);
    if (this.triggerElement.length > 1) {
      throw new Error("You can't call Opentip on multiple elements.");
    }
    if (this.triggerElement.length < 1) {
      throw new Error("Invalid element.");
    }
    this.loaded = false;
    this.loading = false;
    this.visible = false;
    this.waitingToShow = false;
    this.waitingToHide = false;
    this.currentPosition = {
      left: 0,
      top: 0
    };
    this.dimensions = {
      width: 100,
      height: 50
    };
    this.content = "";
    this.redraw = true;
    this.currentObservers = {
      showing: false,
      visible: false,
      hiding: false,
      hidden: false
    };
    options = this.adapter.clone(options);
    if (typeof content === "object") {
      options = content;
      content = title = void 0;
    } else if (typeof title === "object") {
      options = title;
      title = void 0;
    }
    if (title != null) {
      options.title = title;
    }
    if (content != null) {
      this.setContent(content);
    }
    if (options["extends"] == null) {
      if (options.style != null) {
        options["extends"] = options.style;
      } else {
        options["extends"] = Opentip.defaultStyle;
      }
    }
    optionSources = [options];
    _tmpStyle = options;
    while (_tmpStyle["extends"]) {
      styleName = _tmpStyle["extends"];
      _tmpStyle = Opentip.styles[styleName];
      if (_tmpStyle == null) {
        throw new Error("Invalid style: " + styleName);
      }
      optionSources.unshift(_tmpStyle);
      if (!((_tmpStyle["extends"] != null) || styleName === "standard")) {
        _tmpStyle["extends"] = "standard";
      }
    }
    options = (_ref = this.adapter).extend.apply(_ref, [{}].concat(__slice.call(optionSources)));
    options.hideTriggers = (function() {
      var _i, _len, _ref1, _results;
      _ref1 = options.hideTriggers;
      _results = [];
      for (_i = 0, _len = _ref1.length; _i < _len; _i++) {
        hideTrigger = _ref1[_i];
        _results.push(hideTrigger);
      }
      return _results;
    })();
    if (options.hideTrigger && options.hideTriggers.length === 0) {
      options.hideTriggers.push(options.hideTrigger);
    }
    _ref1 = ["tipJoint", "targetJoint", "stem"];
    for (_i = 0, _len = _ref1.length; _i < _len; _i++) {
      prop = _ref1[_i];
      if (options[prop] && typeof options[prop] === "string") {
        options[prop] = new Opentip.Joint(options[prop]);
      }
    }
    if (options.ajax && (options.ajax === true || !options.ajax)) {
      if (this.adapter.tagName(this.triggerElement) === "A") {
        options.ajax = this.adapter.attr(this.triggerElement, "href");
      } else {
        options.ajax = false;
      }
    }
    if (options.showOn === "click" && this.adapter.tagName(this.triggerElement) === "A") {
      this.adapter.observe(this.triggerElement, "click", function(e) {
        e.preventDefault();
        e.stopPropagation();
        return e.stopped = true;
      });
    }
    if (options.target) {
      options.fixed = true;
    }
    if (options.stem === true) {
      options.stem = new Opentip.Joint(options.tipJoint);
    }
    if (options.target === true) {
      options.target = this.triggerElement;
    } else if (options.target) {
      options.target = this.adapter.wrap(options.target);
    }
    this.currentStem = options.stem;
    if (options.delay == null) {
      options.delay = options.showOn === "mouseover" ? 0.2 : 0;
    }
    if (options.targetJoint == null) {
      options.targetJoint = new Opentip.Joint(options.tipJoint).flip();
    }
    this.showTriggers = [];
    this.showTriggersWhenVisible = [];
    this.hideTriggers = [];
    if (options.showOn && options.showOn !== "creation") {
      this.showTriggers.push({
        element: this.triggerElement,
        event: options.showOn
      });
    }
    if (options.ajaxCache != null) {
      options.cache = options.ajaxCache;
      delete options.ajaxCache;
    }
    this.options = options;
    this.bound = {};
    _ref2 = ["prepareToShow", "prepareToHide", "show", "hide", "reposition"];
    for (_j = 0, _len1 = _ref2.length; _j < _len1; _j++) {
      methodToBind = _ref2[_j];
      this.bound[methodToBind] = (function(methodToBind) {
        return function() {
          return _this[methodToBind].apply(_this, arguments);
        };
      })(methodToBind);
    }
    this.adapter.domReady(function() {
      _this.activate();
      if (_this.options.showOn === "creation") {
        return _this.prepareToShow();
      }
    });
  }

  Opentip.prototype._setup = function() {
    var hideOn, hideTrigger, hideTriggerElement, i, _i, _j, _len, _len1, _ref, _ref1, _results;
    this.debug("Setting up the tooltip.");
    this._buildContainer();
    this.hideTriggers = [];
    _ref = this.options.hideTriggers;
    for (i = _i = 0, _len = _ref.length; _i < _len; i = ++_i) {
      hideTrigger = _ref[i];
      hideTriggerElement = null;
      hideOn = this.options.hideOn instanceof Array ? this.options.hideOn[i] : this.options.hideOn;
      if (typeof hideTrigger === "string") {
        switch (hideTrigger) {
          case "trigger":
            hideOn = hideOn || "mouseout";
            hideTriggerElement = this.triggerElement;
            break;
          case "tip":
            hideOn = hideOn || "mouseover";
            hideTriggerElement = this.container;
            break;
          case "target":
            hideOn = hideOn || "mouseover";
            hideTriggerElement = this.options.target;
            break;
          case "closeButton":
            break;
          default:
            throw new Error("Unknown hide trigger: " + hideTrigger + ".");
        }
      } else {
        hideOn = hideOn || "mouseover";
        hideTriggerElement = this.adapter.wrap(hideTrigger);
      }
      if (hideTriggerElement) {
        this.hideTriggers.push({
          element: hideTriggerElement,
          event: hideOn,
          original: hideTrigger
        });
      }
    }
    _ref1 = this.hideTriggers;
    _results = [];
    for (_j = 0, _len1 = _ref1.length; _j < _len1; _j++) {
      hideTrigger = _ref1[_j];
      _results.push(this.showTriggersWhenVisible.push({
        element: hideTrigger.element,
        event: "mouseover"
      }));
    }
    return _results;
  };

  Opentip.prototype._buildContainer = function() {
    this.container = this.adapter.create("<div id=\"opentip-" + this.id + "\" class=\"" + this["class"].container + " " + this["class"].hidden + " " + this["class"].stylePrefix + this.options.className + "\"></div>");
    this.adapter.css(this.container, {
      position: "absolute"
    });
    if (this.options.ajax) {
      this.adapter.addClass(this.container, this["class"].loading);
    }
    if (this.options.fixed) {
      this.adapter.addClass(this.container, this["class"].fixed);
    }
    if (this.options.showEffect) {
      this.adapter.addClass(this.container, "" + this["class"].showEffectPrefix + this.options.showEffect);
    }
    if (this.options.hideEffect) {
      return this.adapter.addClass(this.container, "" + this["class"].hideEffectPrefix + this.options.hideEffect);
    }
  };

  Opentip.prototype._buildElements = function() {
    var headerElement, titleElement;
    this.tooltipElement = this.adapter.create("<div class=\"" + this["class"].opentip + "\"><div class=\"" + this["class"].header + "\"></div><div class=\"" + this["class"].content + "\"></div></div>");
    this.backgroundCanvas = this.adapter.wrap(document.createElement("canvas"));
    this.adapter.css(this.backgroundCanvas, {
      position: "absolute"
    });
    if (typeof G_vmlCanvasManager !== "undefined" && G_vmlCanvasManager !== null) {
      G_vmlCanvasManager.initElement(this.adapter.unwrap(this.backgroundCanvas));
    }
    headerElement = this.adapter.find(this.tooltipElement, "." + this["class"].header);
    if (this.options.title) {
      titleElement = this.adapter.create("<h1></h1>");
      this.adapter.update(titleElement, this.options.title, this.options.escapeTitle);
      this.adapter.append(headerElement, titleElement);
    }
    if (this.options.ajax && !this.loaded) {
      this.adapter.append(this.tooltipElement, this.adapter.create("<div class=\"" + this["class"].loadingIndicator + "\"><span>↻</span></div>"));
    }
    if (__indexOf.call(this.options.hideTriggers, "closeButton") >= 0) {
      this.closeButtonElement = this.adapter.create("<a href=\"javascript:undefined;\" class=\"" + this["class"].close + "\"><span>Close</span></a>");
      this.adapter.append(headerElement, this.closeButtonElement);
    }
    this.adapter.append(this.container, this.backgroundCanvas);
    this.adapter.append(this.container, this.tooltipElement);
    this.adapter.append(document.body, this.container);
    this._newContent = true;
    return this.redraw = true;
  };

  Opentip.prototype.setContent = function(content) {
    this.content = content;
    this._newContent = true;
    if (typeof this.content === "function") {
      this._contentFunction = this.content;
      this.content = "";
    } else {
      this._contentFunction = null;
    }
    if (this.visible) {
      return this._updateElementContent();
    }
  };

  Opentip.prototype._updateElementContent = function() {
    var contentDiv;
    if (this._newContent || (!this.options.cache && this._contentFunction)) {
      contentDiv = this.adapter.find(this.container, "." + this["class"].content);
      if (contentDiv != null) {
        if (this._contentFunction) {
          this.debug("Executing content function.");
          this.content = this._contentFunction(this);
        }
        this.adapter.update(contentDiv, this.content, this.options.escapeContent);
      }
      this._newContent = false;
    }
    this._storeAndLockDimensions();
    return this.reposition();
  };

  Opentip.prototype._storeAndLockDimensions = function() {
    var prevDimension;
    if (!this.container) {
      return;
    }
    prevDimension = this.dimensions;
    this.adapter.css(this.container, {
      width: "auto",
      left: "0px",
      top: "0px"
    });
    this.dimensions = this.adapter.dimensions(this.container);
    this.dimensions.width += 1;
    this.adapter.css(this.container, {
      width: "" + this.dimensions.width + "px",
      top: "" + this.currentPosition.top + "px",
      left: "" + this.currentPosition.left + "px"
    });
    if (!this._dimensionsEqual(this.dimensions, prevDimension)) {
      this.redraw = true;
      return this._draw();
    }
  };

  Opentip.prototype.activate = function() {
    return this._setupObservers("hidden", "hiding");
  };

  Opentip.prototype.deactivate = function() {
    this.debug("Deactivating tooltip.");
    this.hide();
    return this._setupObservers("-showing", "-visible", "-hidden", "-hiding");
  };

  Opentip.prototype._setupObservers = function() {
    var observeOrStop, removeObserver, state, states, trigger, _i, _j, _k, _l, _len, _len1, _len2, _len3, _ref, _ref1, _ref2,
      _this = this;
    states = 1 <= arguments.length ? __slice.call(arguments, 0) : [];
    for (_i = 0, _len = states.length; _i < _len; _i++) {
      state = states[_i];
      removeObserver = false;
      if (state.charAt(0) === "-") {
        removeObserver = true;
        state = state.substr(1);
      }
      if (this.currentObservers[state] === !removeObserver) {
        continue;
      }
      this.currentObservers[state] = !removeObserver;
      observeOrStop = function() {
        var args, _ref, _ref1;
        args = 1 <= arguments.length ? __slice.call(arguments, 0) : [];
        if (removeObserver) {
          return (_ref = _this.adapter).stopObserving.apply(_ref, args);
        } else {
          return (_ref1 = _this.adapter).observe.apply(_ref1, args);
        }
      };
      switch (state) {
        case "showing":
          _ref = this.hideTriggers;
          for (_j = 0, _len1 = _ref.length; _j < _len1; _j++) {
            trigger = _ref[_j];
            observeOrStop(trigger.element, trigger.event, this.bound.prepareToHide);
          }
          observeOrStop((document.onresize != null ? document : window), "resize", this.bound.reposition);
          observeOrStop(window, "scroll", this.bound.reposition);
          break;
        case "visible":
          _ref1 = this.showTriggersWhenVisible;
          for (_k = 0, _len2 = _ref1.length; _k < _len2; _k++) {
            trigger = _ref1[_k];
            observeOrStop(trigger.element, trigger.event, this.bound.prepareToShow);
          }
          break;
        case "hiding":
          _ref2 = this.showTriggers;
          for (_l = 0, _len3 = _ref2.length; _l < _len3; _l++) {
            trigger = _ref2[_l];
            observeOrStop(trigger.element, trigger.event, this.bound.prepareToShow);
          }
          break;
        case "hidden":
          break;
        default:
          throw new Error("Unknown state: " + state);
      }
    }
    return null;
  };

  Opentip.prototype.prepareToShow = function() {
    this._abortHiding();
    this._abortShowing();
    if (this.visible) {
      return;
    }
    this.debug("Showing in " + this.options.delay + "s.");
    if (this.container == null) {
      this._setup();
    }
    if (this.options.group) {
      Opentip._abortShowingGroup(this.options.group, this);
    }
    this.preparingToShow = true;
    this._setupObservers("-hidden", "-hiding", "showing");
    this._followMousePosition();
    if (this.options.fixed && !this.options.target) {
      this.initialMousePosition = mousePosition;
    }
    this.reposition();
    return this._showTimeoutId = this.setTimeout(this.bound.show, this.options.delay || 0);
  };

  Opentip.prototype.show = function() {
    var _this = this;
    this._abortHiding();
    if (this.visible) {
      return;
    }
    this._clearTimeouts();
    if (!this._triggerElementExists()) {
      return this.deactivate();
    }
    this.debug("Showing now.");
    if (this.container == null) {
      this._setup();
    }
    if (this.options.group) {
      Opentip._hideGroup(this.options.group, this);
    }
    this.visible = true;
    this.preparingToShow = false;
    if (this.tooltipElement == null) {
      this._buildElements();
    }
    this._updateElementContent();
    if (this.options.ajax && (!this.loaded || !this.options.cache)) {
      this._loadAjax();
    }
    this._searchAndActivateCloseButtons();
    this._startEnsureTriggerElement();
    this.adapter.css(this.container, {
      zIndex: Opentip.lastZIndex++
    });
    this._setupObservers("-hidden", "-hiding", "-showing", "-visible", "showing", "visible");
    if (this.options.fixed && !this.options.target) {
      this.initialMousePosition = mousePosition;
    }
    this.reposition();
    this.adapter.removeClass(this.container, this["class"].hiding);
    this.adapter.removeClass(this.container, this["class"].hidden);
    this.adapter.addClass(this.container, this["class"].goingToShow);
    this.setCss3Style(this.container, {
      transitionDuration: "0s"
    });
    this.defer(function() {
      var delay;
      if (!_this.visible || _this.preparingToHide) {
        return;
      }
      _this.adapter.removeClass(_this.container, _this["class"].goingToShow);
      _this.adapter.addClass(_this.container, _this["class"].showing);
      delay = 0;
      if (_this.options.showEffect && _this.options.showEffectDuration) {
        delay = _this.options.showEffectDuration;
      }
      _this.setCss3Style(_this.container, {
        transitionDuration: "" + delay + "s"
      });
      _this._visibilityStateTimeoutId = _this.setTimeout(function() {
        _this.adapter.removeClass(_this.container, _this["class"].showing);
        return _this.adapter.addClass(_this.container, _this["class"].visible);
      }, delay);
      return _this._activateFirstInput();
    });
    return this._draw();
  };

  Opentip.prototype._abortShowing = function() {
    if (this.preparingToShow) {
      this.debug("Aborting showing.");
      this._clearTimeouts();
      this._stopFollowingMousePosition();
      this.preparingToShow = false;
      return this._setupObservers("-showing", "-visible", "hiding", "hidden");
    }
  };

  Opentip.prototype.prepareToHide = function() {
    this._abortShowing();
    this._abortHiding();
    if (!this.visible) {
      return;
    }
    this.debug("Hiding in " + this.options.hideDelay + "s");
    this.preparingToHide = true;
    this._setupObservers("-showing", "visible", "-hidden", "hiding");
    return this._hideTimeoutId = this.setTimeout(this.bound.hide, this.options.hideDelay);
  };

  Opentip.prototype.hide = function() {
    var _this = this;
    this._abortShowing();
    if (!this.visible) {
      return;
    }
    this._clearTimeouts();
    this.debug("Hiding!");
    this.visible = false;
    this.preparingToHide = false;
    this._stopEnsureTriggerElement();
    this._setupObservers("-showing", "-visible", "-hiding", "-hidden", "hiding", "hidden");
    if (!this.options.fixed) {
      this._stopFollowingMousePosition();
    }
    if (!this.container) {
      return;
    }
    this.adapter.removeClass(this.container, this["class"].visible);
    this.adapter.removeClass(this.container, this["class"].showing);
    this.adapter.addClass(this.container, this["class"].goingToHide);
    this.setCss3Style(this.container, {
      transitionDuration: "0s"
    });
    return this.defer(function() {
      var hideDelay;
      _this.adapter.removeClass(_this.container, _this["class"].goingToHide);
      _this.adapter.addClass(_this.container, _this["class"].hiding);
      hideDelay = 0;
      if (_this.options.hideEffect && _this.options.hideEffectDuration) {
        hideDelay = _this.options.hideEffectDuration;
      }
      _this.setCss3Style(_this.container, {
        transitionDuration: "" + hideDelay + "s"
      });
      return _this._visibilityStateTimeoutId = _this.setTimeout(function() {
        _this.adapter.removeClass(_this.container, _this["class"].hiding);
        _this.adapter.addClass(_this.container, _this["class"].hidden);
        _this.setCss3Style(_this.container, {
          transitionDuration: "0s"
        });
        if (_this.options.removeElementsOnHide) {
          _this.debug("Removing HTML elements.");
          _this.adapter.remove(_this.container);
          delete _this.container;
          return delete _this.tooltipElement;
        }
      }, hideDelay);
    });
  };

  Opentip.prototype._abortHiding = function() {
    if (this.preparingToHide) {
      this.debug("Aborting hiding.");
      this._clearTimeouts();
      this.preparingToHide = false;
      return this._setupObservers("-hiding", "showing", "visible");
    }
  };

  Opentip.prototype.reposition = function() {
    var position, stem, _ref,
      _this = this;
    position = this.getPosition();
    if (position == null) {
      return;
    }
    stem = this.options.stem;
    if (this.options.containInViewport) {
      _ref = this._ensureViewportContainment(position), position = _ref.position, stem = _ref.stem;
    }
    if (this._positionsEqual(position, this.currentPosition)) {
      return;
    }
    if (!(!this.options.stem || stem.eql(this.currentStem))) {
      this.redraw = true;
    }
    this.currentPosition = position;
    this.currentStem = stem;
    this._draw();
    this.adapter.css(this.container, {
      left: "" + position.left + "px",
      top: "" + position.top + "px"
    });
    return this.defer(function() {
      var rawContainer, redrawFix;
      rawContainer = _this.adapter.unwrap(_this.container);
      rawContainer.style.visibility = "hidden";
      redrawFix = rawContainer.offsetHeight;
      return rawContainer.style.visibility = "visible";
    });
  };

  Opentip.prototype.getPosition = function(tipJoint, targetJoint, stem) {
    var additionalHorizontal, additionalVertical, offsetDistance, position, stemLength, targetDimensions, targetPosition, unwrappedTarget, _ref;
    if (!this.container) {
      return;
    }
    if (tipJoint == null) {
      tipJoint = this.options.tipJoint;
    }
    if (targetJoint == null) {
      targetJoint = this.options.targetJoint;
    }
    position = {};
    if (this.options.target) {
      targetPosition = this.adapter.offset(this.options.target);
      targetDimensions = this.adapter.dimensions(this.options.target);
      position = targetPosition;
      if (targetJoint.right) {
        unwrappedTarget = this.adapter.unwrap(this.options.target);
        if (unwrappedTarget.getBoundingClientRect != null) {
          position.left = unwrappedTarget.getBoundingClientRect().right + ((_ref = window.pageXOffset) != null ? _ref : document.body.scrollLeft);
        } else {
          position.left += targetDimensions.width;
        }
      } else if (targetJoint.center) {
        position.left += Math.round(targetDimensions.width / 2);
      }
      if (targetJoint.bottom) {
        position.top += targetDimensions.height;
      } else if (targetJoint.middle) {
        position.top += Math.round(targetDimensions.height / 2);
      }
      if (this.options.borderWidth) {
        if (this.options.tipJoint.left) {
          position.left += this.options.borderWidth;
        }
        if (this.options.tipJoint.right) {
          position.left -= this.options.borderWidth;
        }
        if (this.options.tipJoint.top) {
          position.top += this.options.borderWidth;
        } else if (this.options.tipJoint.bottom) {
          position.top -= this.options.borderWidth;
        }
      }
    } else {
      if (this.initialMousePosition) {
        position = {
          top: this.initialMousePosition.y,
          left: this.initialMousePosition.x
        };
      } else {
        position = {
          top: mousePosition.y,
          left: mousePosition.x
        };
      }
    }
    if (this.options.autoOffset) {
      stemLength = this.options.stem ? this.options.stemLength : 0;
      offsetDistance = stemLength && this.options.fixed ? 2 : 10;
      additionalHorizontal = tipJoint.middle && !this.options.fixed ? 15 : 0;
      additionalVertical = tipJoint.center && !this.options.fixed ? 15 : 0;
      if (tipJoint.right) {
        position.left -= offsetDistance + additionalHorizontal;
      } else if (tipJoint.left) {
        position.left += offsetDistance + additionalHorizontal;
      }
      if (tipJoint.bottom) {
        position.top -= offsetDistance + additionalVertical;
      } else if (tipJoint.top) {
        position.top += offsetDistance + additionalVertical;
      }
      if (stemLength) {
        if (stem == null) {
          stem = this.options.stem;
        }
        if (stem.right) {
          position.left -= stemLength;
        } else if (stem.left) {
          position.left += stemLength;
        }
        if (stem.bottom) {
          position.top -= stemLength;
        } else if (stem.top) {
          position.top += stemLength;
        }
      }
    }
    position.left += this.options.offset[0];
    position.top += this.options.offset[1];
    if (tipJoint.right) {
      position.left -= this.dimensions.width;
    } else if (tipJoint.center) {
      position.left -= Math.round(this.dimensions.width / 2);
    }
    if (tipJoint.bottom) {
      position.top -= this.dimensions.height;
    } else if (tipJoint.middle) {
      position.top -= Math.round(this.dimensions.height / 2);
    }
    return position;
  };

  Opentip.prototype._ensureViewportContainment = function(position) {
    var needsRepositioning, newSticksOut, originals, revertedX, revertedY, scrollOffset, stem, sticksOut, targetJoint, tipJoint, viewportDimensions, viewportPosition;
    stem = this.options.stem;
    originals = {
      position: position,
      stem: stem
    };
    if (!(this.visible && position)) {
      return originals;
    }
    sticksOut = this._sticksOut(position);
    if (!(sticksOut[0] || sticksOut[1])) {
      return originals;
    }
    tipJoint = new Opentip.Joint(this.options.tipJoint);
    if (this.options.targetJoint) {
      targetJoint = new Opentip.Joint(this.options.targetJoint);
    }
    scrollOffset = this.adapter.scrollOffset();
    viewportDimensions = this.adapter.viewportDimensions();
    viewportPosition = [position.left - scrollOffset[0], position.top - scrollOffset[1]];
    needsRepositioning = false;
    if (viewportDimensions.width >= this.dimensions.width) {
      if (sticksOut[0]) {
        needsRepositioning = true;
        switch (sticksOut[0]) {
          case this.STICKS_OUT_LEFT:
            tipJoint.setHorizontal("left");
            if (this.options.targetJoint) {
              targetJoint.setHorizontal("right");
            }
            break;
          case this.STICKS_OUT_RIGHT:
            tipJoint.setHorizontal("right");
            if (this.options.targetJoint) {
              targetJoint.setHorizontal("left");
            }
        }
      }
    }
    if (viewportDimensions.height >= this.dimensions.height) {
      if (sticksOut[1]) {
        needsRepositioning = true;
        switch (sticksOut[1]) {
          case this.STICKS_OUT_TOP:
            tipJoint.setVertical("top");
            if (this.options.targetJoint) {
              targetJoint.setVertical("bottom");
            }
            break;
          case this.STICKS_OUT_BOTTOM:
            tipJoint.setVertical("bottom");
            if (this.options.targetJoint) {
              targetJoint.setVertical("top");
            }
        }
      }
    }
    if (!needsRepositioning) {
      return originals;
    }
    if (this.options.stem) {
      stem = tipJoint;
    }
    position = this.getPosition(tipJoint, targetJoint, stem);
    newSticksOut = this._sticksOut(position);
    revertedX = false;
    revertedY = false;
    if (newSticksOut[0] && (newSticksOut[0] !== sticksOut[0])) {
      revertedX = true;
      tipJoint.setHorizontal(this.options.tipJoint.horizontal);
      if (this.options.targetJoint) {
        targetJoint.setHorizontal(this.options.targetJoint.horizontal);
      }
    }
    if (newSticksOut[1] && (newSticksOut[1] !== sticksOut[1])) {
      revertedY = true;
      tipJoint.setVertical(this.options.tipJoint.vertical);
      if (this.options.targetJoint) {
        targetJoint.setVertical(this.options.targetJoint.vertical);
      }
    }
    if (revertedX && revertedY) {
      return originals;
    }
    if (revertedX || revertedY) {
      if (this.options.stem) {
        stem = tipJoint;
      }
      position = this.getPosition(tipJoint, targetJoint, stem);
    }
    return {
      position: position,
      stem: stem
    };
  };

  Opentip.prototype._sticksOut = function(position) {
    var positionOffset, scrollOffset, sticksOut, viewportDimensions;
    scrollOffset = this.adapter.scrollOffset();
    viewportDimensions = this.adapter.viewportDimensions();
    positionOffset = [position.left - scrollOffset[0], position.top - scrollOffset[1]];
    sticksOut = [false, false];
    if (positionOffset[0] < 0) {
      sticksOut[0] = this.STICKS_OUT_LEFT;
    } else if (positionOffset[0] + this.dimensions.width > viewportDimensions.width) {
      sticksOut[0] = this.STICKS_OUT_RIGHT;
    }
    if (positionOffset[1] < 0) {
      sticksOut[1] = this.STICKS_OUT_TOP;
    } else if (positionOffset[1] + this.dimensions.height > viewportDimensions.height) {
      sticksOut[1] = this.STICKS_OUT_BOTTOM;
    }
    return sticksOut;
  };

  Opentip.prototype._draw = function() {
    var backgroundCanvas, bulge, canvasDimensions, canvasPosition, closeButton, closeButtonInner, closeButtonOuter, ctx, drawCorner, drawLine, hb, position, stemBase, stemLength, _i, _len, _ref, _ref1, _ref2,
      _this = this;
    if (!(this.backgroundCanvas && this.redraw)) {
      return;
    }
    this.debug("Drawing background.");
    this.redraw = false;
    if (this.currentStem) {
      _ref = ["top", "right", "bottom", "left"];
      for (_i = 0, _len = _ref.length; _i < _len; _i++) {
        position = _ref[_i];
        this.adapter.removeClass(this.container, "stem-" + position);
      }
      this.adapter.addClass(this.container, "stem-" + this.currentStem.horizontal);
      this.adapter.addClass(this.container, "stem-" + this.currentStem.vertical);
    }
    closeButtonInner = [0, 0];
    closeButtonOuter = [0, 0];
    if (__indexOf.call(this.options.hideTriggers, "closeButton") >= 0) {
      closeButton = new Opentip.Joint(((_ref1 = this.currentStem) != null ? _ref1.toString() : void 0) === "top right" ? "top left" : "top right");
      closeButtonInner = [this.options.closeButtonRadius + this.options.closeButtonOffset[0], this.options.closeButtonRadius + this.options.closeButtonOffset[1]];
      closeButtonOuter = [this.options.closeButtonRadius - this.options.closeButtonOffset[0], this.options.closeButtonRadius - this.options.closeButtonOffset[1]];
    }
    canvasDimensions = this.adapter.clone(this.dimensions);
    canvasPosition = [0, 0];
    if (this.options.borderWidth) {
      canvasDimensions.width += this.options.borderWidth * 2;
      canvasDimensions.height += this.options.borderWidth * 2;
      canvasPosition[0] -= this.options.borderWidth;
      canvasPosition[1] -= this.options.borderWidth;
    }
    if (this.options.shadow) {
      canvasDimensions.width += this.options.shadowBlur * 2;
      canvasDimensions.width += Math.max(0, this.options.shadowOffset[0] - this.options.shadowBlur * 2);
      canvasDimensions.height += this.options.shadowBlur * 2;
      canvasDimensions.height += Math.max(0, this.options.shadowOffset[1] - this.options.shadowBlur * 2);
      canvasPosition[0] -= Math.max(0, this.options.shadowBlur - this.options.shadowOffset[0]);
      canvasPosition[1] -= Math.max(0, this.options.shadowBlur - this.options.shadowOffset[1]);
    }
    bulge = {
      left: 0,
      right: 0,
      top: 0,
      bottom: 0
    };
    if (this.currentStem) {
      if (this.currentStem.left) {
        bulge.left = this.options.stemLength;
      } else if (this.currentStem.right) {
        bulge.right = this.options.stemLength;
      }
      if (this.currentStem.top) {
        bulge.top = this.options.stemLength;
      } else if (this.currentStem.bottom) {
        bulge.bottom = this.options.stemLength;
      }
    }
    if (closeButton) {
      if (closeButton.left) {
        bulge.left = Math.max(bulge.left, closeButtonOuter[0]);
      } else if (closeButton.right) {
        bulge.right = Math.max(bulge.right, closeButtonOuter[0]);
      }
      if (closeButton.top) {
        bulge.top = Math.max(bulge.top, closeButtonOuter[1]);
      } else if (closeButton.bottom) {
        bulge.bottom = Math.max(bulge.bottom, closeButtonOuter[1]);
      }
    }
    canvasDimensions.width += bulge.left + bulge.right;
    canvasDimensions.height += bulge.top + bulge.bottom;
    canvasPosition[0] -= bulge.left;
    canvasPosition[1] -= bulge.top;
    if (this.currentStem && this.options.borderWidth) {
      _ref2 = this._getPathStemMeasures(this.options.stemBase, this.options.stemLength, this.options.borderWidth), stemLength = _ref2.stemLength, stemBase = _ref2.stemBase;
    }
    backgroundCanvas = this.adapter.unwrap(this.backgroundCanvas);
    backgroundCanvas.width = canvasDimensions.width;
    backgroundCanvas.height = canvasDimensions.height;
    this.adapter.css(this.backgroundCanvas, {
      width: "" + backgroundCanvas.width + "px",
      height: "" + backgroundCanvas.height + "px",
      left: "" + canvasPosition[0] + "px",
      top: "" + canvasPosition[1] + "px"
    });
    ctx = backgroundCanvas.getContext("2d");
    ctx.setTransform(1, 0, 0, 1, 0, 0);
    ctx.clearRect(0, 0, backgroundCanvas.width, backgroundCanvas.height);
    ctx.beginPath();
    ctx.fillStyle = this._getColor(ctx, this.dimensions, this.options.background, this.options.backgroundGradientHorizontal);
    ctx.lineJoin = "miter";
    ctx.miterLimit = 500;
    hb = this.options.borderWidth / 2;
    if (this.options.borderWidth) {
      ctx.strokeStyle = this.options.borderColor;
      ctx.lineWidth = this.options.borderWidth;
    } else {
      stemLength = this.options.stemLength;
      stemBase = this.options.stemBase;
    }
    if (stemBase == null) {
      stemBase = 0;
    }
    drawLine = function(length, stem, first) {
      if (first) {
        ctx.moveTo(Math.max(stemBase, _this.options.borderRadius, closeButtonInner[0]) + 1 - hb, -hb);
      }
      if (stem) {
        ctx.lineTo(length / 2 - stemBase / 2, -hb);
        ctx.lineTo(length / 2, -stemLength - hb);
        return ctx.lineTo(length / 2 + stemBase / 2, -hb);
      }
    };
    drawCorner = function(stem, closeButton, i) {
      var angle1, angle2, innerWidth, offset;
      if (stem) {
        ctx.lineTo(-stemBase + hb, 0 - hb);
        ctx.lineTo(stemLength + hb, -stemLength - hb);
        return ctx.lineTo(hb, stemBase - hb);
      } else if (closeButton) {
        offset = _this.options.closeButtonOffset;
        innerWidth = closeButtonInner[0];
        if (i % 2 !== 0) {
          offset = [offset[1], offset[0]];
          innerWidth = closeButtonInner[1];
        }
        angle1 = Math.acos(offset[1] / _this.options.closeButtonRadius);
        angle2 = Math.acos(offset[0] / _this.options.closeButtonRadius);
        ctx.lineTo(-innerWidth + hb, -hb);
        return ctx.arc(hb - offset[0], -hb + offset[1], _this.options.closeButtonRadius, -(Math.PI / 2 + angle1), angle2, false);
      } else {
        ctx.lineTo(-_this.options.borderRadius + hb, -hb);
        return ctx.quadraticCurveTo(hb, -hb, hb, _this.options.borderRadius - hb);
      }
    };
    ctx.translate(-canvasPosition[0], -canvasPosition[1]);
    ctx.save();
    (function() {
      var cornerStem, i, lineLength, lineStem, positionIdx, positionX, positionY, rotation, _j, _ref3, _results;
      _results = [];
      for (i = _j = 0, _ref3 = Opentip.positions.length / 2; 0 <= _ref3 ? _j < _ref3 : _j > _ref3; i = 0 <= _ref3 ? ++_j : --_j) {
        positionIdx = i * 2;
        positionX = i === 0 || i === 3 ? 0 : _this.dimensions.width;
        positionY = i < 2 ? 0 : _this.dimensions.height;
        rotation = (Math.PI / 2) * i;
        lineLength = i % 2 === 0 ? _this.dimensions.width : _this.dimensions.height;
        lineStem = new Opentip.Joint(Opentip.positions[positionIdx]);
        cornerStem = new Opentip.Joint(Opentip.positions[positionIdx + 1]);
        ctx.save();
        ctx.translate(positionX, positionY);
        ctx.rotate(rotation);
        drawLine(lineLength, lineStem.eql(_this.currentStem), i === 0);
        ctx.translate(lineLength, 0);
        drawCorner(cornerStem.eql(_this.currentStem), cornerStem.eql(closeButton), i);
        _results.push(ctx.restore());
      }
      return _results;
    })();
    ctx.closePath();
    ctx.save();
    if (this.options.shadow) {
      ctx.shadowColor = this.options.shadowColor;
      ctx.shadowBlur = this.options.shadowBlur;
      ctx.shadowOffsetX = this.options.shadowOffset[0];
      ctx.shadowOffsetY = this.options.shadowOffset[1];
    }
    ctx.fill();
    ctx.restore();
    if (this.options.borderWidth) {
      ctx.stroke();
    }
    ctx.restore();
    if (closeButton) {
      return (function() {
        var crossCenter, crossHeight, crossWidth, hcs, linkCenter;
        crossWidth = crossHeight = _this.options.closeButtonRadius * 2;
        if (closeButton.toString() === "top right") {
          linkCenter = [_this.dimensions.width - _this.options.closeButtonOffset[0], _this.options.closeButtonOffset[1]];
          crossCenter = [linkCenter[0] + hb, linkCenter[1] - hb];
        } else {
          linkCenter = [_this.options.closeButtonOffset[0], _this.options.closeButtonOffset[1]];
          crossCenter = [linkCenter[0] - hb, linkCenter[1] - hb];
        }
        ctx.translate(crossCenter[0], crossCenter[1]);
        hcs = _this.options.closeButtonCrossSize / 2;
        ctx.save();
        ctx.beginPath();
        ctx.strokeStyle = _this.options.closeButtonCrossColor;
        ctx.lineWidth = _this.options.closeButtonCrossLineWidth;
        ctx.lineCap = "round";
        ctx.moveTo(-hcs, -hcs);
        ctx.lineTo(hcs, hcs);
        ctx.stroke();
        ctx.beginPath();
        ctx.moveTo(hcs, -hcs);
        ctx.lineTo(-hcs, hcs);
        ctx.stroke();
        ctx.restore();
        return _this.adapter.css(_this.closeButtonElement, {
          left: "" + (linkCenter[0] - hcs - _this.options.closeButtonLinkOverscan) + "px",
          top: "" + (linkCenter[1] - hcs - _this.options.closeButtonLinkOverscan) + "px",
          width: "" + (_this.options.closeButtonCrossSize + _this.options.closeButtonLinkOverscan * 2) + "px",
          height: "" + (_this.options.closeButtonCrossSize + _this.options.closeButtonLinkOverscan * 2) + "px"
        });
      })();
    }
  };

  Opentip.prototype._getPathStemMeasures = function(outerStemBase, outerStemLength, borderWidth) {
    var angle, distanceBetweenTips, halfAngle, hb, rhombusSide, stemBase, stemLength;
    hb = borderWidth / 2;
    halfAngle = Math.atan((outerStemBase / 2) / outerStemLength);
    angle = halfAngle * 2;
    rhombusSide = hb / Math.sin(angle);
    distanceBetweenTips = 2 * rhombusSide * Math.cos(halfAngle);
    stemLength = hb + outerStemLength - distanceBetweenTips;
    if (stemLength < 0) {
      throw new Error("Sorry but your stemLength / stemBase ratio is strange.");
    }
    stemBase = (Math.tan(halfAngle) * stemLength) * 2;
    return {
      stemLength: stemLength,
      stemBase: stemBase
    };
  };

  Opentip.prototype._getColor = function(ctx, dimensions, color, horizontal) {
    var colorStop, gradient, i, _i, _len;
    if (horizontal == null) {
      horizontal = false;
    }
    if (typeof color === "string") {
      return color;
    }
    if (horizontal) {
      gradient = ctx.createLinearGradient(0, 0, dimensions.width, 0);
    } else {
      gradient = ctx.createLinearGradient(0, 0, 0, dimensions.height);
    }
    for (i = _i = 0, _len = color.length; _i < _len; i = ++_i) {
      colorStop = color[i];
      gradient.addColorStop(colorStop[0], colorStop[1]);
    }
    return gradient;
  };

  Opentip.prototype._searchAndActivateCloseButtons = function() {
    var element, _i, _len, _ref;
    _ref = this.adapter.findAll(this.container, "." + this["class"].close);
    for (_i = 0, _len = _ref.length; _i < _len; _i++) {
      element = _ref[_i];
      this.hideTriggers.push({
        element: this.adapter.wrap(element),
        event: "click"
      });
    }
    if (this.currentObservers.showing) {
      this._setupObservers("-showing", "showing");
    }
    if (this.currentObservers.visible) {
      return this._setupObservers("-visible", "visible");
    }
  };

  Opentip.prototype._activateFirstInput = function() {
    var input;
    input = this.adapter.unwrap(this.adapter.find(this.container, "input, textarea"));
    return input != null ? typeof input.focus === "function" ? input.focus() : void 0 : void 0;
  };

  Opentip.prototype._followMousePosition = function() {
    if (!this.options.fixed) {
      return Opentip._observeMousePosition(this.bound.reposition);
    }
  };

  Opentip.prototype._stopFollowingMousePosition = function() {
    if (!this.options.fixed) {
      return Opentip._stopObservingMousePosition(this.bound.reposition);
    }
  };

  Opentip.prototype._clearShowTimeout = function() {
    return clearTimeout(this._showTimeoutId);
  };

  Opentip.prototype._clearHideTimeout = function() {
    return clearTimeout(this._hideTimeoutId);
  };

  Opentip.prototype._clearTimeouts = function() {
    clearTimeout(this._visibilityStateTimeoutId);
    this._clearShowTimeout();
    return this._clearHideTimeout();
  };

  Opentip.prototype._triggerElementExists = function() {
    var el;
    el = this.adapter.unwrap(this.triggerElement);
    while (el.parentNode) {
      if (el.parentNode.tagName === "BODY") {
        return true;
      }
      el = el.parentNode;
    }
    return false;
  };

  Opentip.prototype._loadAjax = function() {
    var _this = this;
    if (this.loading) {
      return;
    }
    this.loaded = false;
    this.loading = true;
    this.adapter.addClass(this.container, this["class"].loading);
    this.setContent("");
    this.debug("Loading content from " + this.options.ajax);
    return this.adapter.ajax({
      url: this.options.ajax,
      method: this.options.ajaxMethod,
      onSuccess: function(responseText) {
        _this.debug("Loading successful.");
        _this.adapter.removeClass(_this.container, _this["class"].loading);
        return _this.setContent(responseText);
      },
      onError: function(error) {
        var message;
        message = _this.options.ajaxErrorMessage;
        _this.debug(message, error);
        _this.setContent(message);
        return _this.adapter.addClass(_this.container, _this["class"].ajaxError);
      },
      onComplete: function() {
        _this.adapter.removeClass(_this.container, _this["class"].loading);
        _this.loading = false;
        _this.loaded = true;
        _this._searchAndActivateCloseButtons();
        _this._activateFirstInput();
        return _this.reposition();
      }
    });
  };

  Opentip.prototype._ensureTriggerElement = function() {
    if (!this._triggerElementExists()) {
      this.deactivate();
      return this._stopEnsureTriggerElement();
    }
  };

  Opentip.prototype._ensureTriggerElementInterval = 1000;

  Opentip.prototype._startEnsureTriggerElement = function() {
    var _this = this;
    return this._ensureTriggerElementTimeoutId = setInterval((function() {
      return _this._ensureTriggerElement();
    }), this._ensureTriggerElementInterval);
  };

  Opentip.prototype._stopEnsureTriggerElement = function() {
    return clearInterval(this._ensureTriggerElementTimeoutId);
  };

  return Opentip;

})();

vendors = ["khtml", "ms", "o", "moz", "webkit"];

Opentip.prototype.setCss3Style = function(element, styles) {
  var prop, value, vendor, vendorProp, _results;
  element = this.adapter.unwrap(element);
  _results = [];
  for (prop in styles) {
    if (!__hasProp.call(styles, prop)) continue;
    value = styles[prop];
    if (element.style[prop] != null) {
      _results.push(element.style[prop] = value);
    } else {
      _results.push((function() {
        var _i, _len, _results1;
        _results1 = [];
        for (_i = 0, _len = vendors.length; _i < _len; _i++) {
          vendor = vendors[_i];
          vendorProp = "" + (this.ucfirst(vendor)) + (this.ucfirst(prop));
          if (element.style[vendorProp] != null) {
            _results1.push(element.style[vendorProp] = value);
          } else {
            _results1.push(void 0);
          }
        }
        return _results1;
      }).call(this));
    }
  }
  return _results;
};

Opentip.prototype.defer = function(func) {
  return setTimeout(func, 0);
};

Opentip.prototype.setTimeout = function(func, seconds) {
  return setTimeout(func, seconds ? seconds * 1000 : 0);
};

Opentip.prototype.ucfirst = function(string) {
  if (string == null) {
    return "";
  }
  return string.charAt(0).toUpperCase() + string.slice(1);
};

Opentip.prototype.dasherize = function(string) {
  return string.replace(/([A-Z])/g, function(_, character) {
    return "-" + (character.toLowerCase());
  });
};

mousePositionObservers = [];

mousePosition = {
  x: 0,
  y: 0
};

mouseMoved = function(e) {
  var observer, _i, _len, _results;
  mousePosition = Opentip.adapter.mousePosition(e);
  _results = [];
  for (_i = 0, _len = mousePositionObservers.length; _i < _len; _i++) {
    observer = mousePositionObservers[_i];
    _results.push(observer());
  }
  return _results;
};

Opentip.followMousePosition = function() {
  return Opentip.adapter.observe(document.body, "mousemove", mouseMoved);
};

Opentip._observeMousePosition = function(observer) {
  return mousePositionObservers.push(observer);
};

Opentip._stopObservingMousePosition = function(removeObserver) {
  var observer;
  return mousePositionObservers = (function() {
    var _i, _len, _results;
    _results = [];
    for (_i = 0, _len = mousePositionObservers.length; _i < _len; _i++) {
      observer = mousePositionObservers[_i];
      if (observer !== removeObserver) {
        _results.push(observer);
      }
    }
    return _results;
  })();
};

Opentip.Joint = (function() {

  function Joint(pointerString) {
    if (pointerString == null) {
      return;
    }
    if (pointerString instanceof Opentip.Joint) {
      pointerString = pointerString.toString();
    }
    this.set(pointerString);
    this;

  }

  Joint.prototype.set = function(string) {
    string = string.toLowerCase();
    this.setHorizontal(string);
    this.setVertical(string);
    return this;
  };

  Joint.prototype.setHorizontal = function(string) {
    var i, valid, _i, _j, _len, _len1, _results;
    valid = ["left", "center", "right"];
    for (_i = 0, _len = valid.length; _i < _len; _i++) {
      i = valid[_i];
      if (~string.indexOf(i)) {
        this.horizontal = i.toLowerCase();
      }
    }
    if (this.horizontal == null) {
      this.horizontal = "center";
    }
    _results = [];
    for (_j = 0, _len1 = valid.length; _j < _len1; _j++) {
      i = valid[_j];
      _results.push(this[i] = this.horizontal === i ? i : void 0);
    }
    return _results;
  };

  Joint.prototype.setVertical = function(string) {
    var i, valid, _i, _j, _len, _len1, _results;
    valid = ["top", "middle", "bottom"];
    for (_i = 0, _len = valid.length; _i < _len; _i++) {
      i = valid[_i];
      if (~string.indexOf(i)) {
        this.vertical = i.toLowerCase();
      }
    }
    if (this.vertical == null) {
      this.vertical = "middle";
    }
    _results = [];
    for (_j = 0, _len1 = valid.length; _j < _len1; _j++) {
      i = valid[_j];
      _results.push(this[i] = this.vertical === i ? i : void 0);
    }
    return _results;
  };

  Joint.prototype.eql = function(pointer) {
    return (pointer != null) && this.horizontal === pointer.horizontal && this.vertical === pointer.vertical;
  };

  Joint.prototype.flip = function() {
    var flippedIndex, positionIdx;
    positionIdx = Opentip.position[this.toString(true)];
    flippedIndex = (positionIdx + 4) % 8;
    this.set(Opentip.positions[flippedIndex]);
    return this;
  };

  Joint.prototype.toString = function(camelized) {
    var horizontal, vertical;
    if (camelized == null) {
      camelized = false;
    }
    vertical = this.vertical === "middle" ? "" : this.vertical;
    horizontal = this.horizontal === "center" ? "" : this.horizontal;
    if (vertical && horizontal) {
      if (camelized) {
        horizontal = Opentip.prototype.ucfirst(horizontal);
      } else {
        horizontal = " " + horizontal;
      }
    }
    return "" + vertical + horizontal;
  };

  return Joint;

})();

Opentip.prototype._positionsEqual = function(posA, posB) {
  return (posA != null) && (posB != null) && posA.left === posB.left && posA.top === posB.top;
};

Opentip.prototype._dimensionsEqual = function(dimA, dimB) {
  return (dimA != null) && (dimB != null) && dimA.width === dimB.width && dimA.height === dimB.height;
};

Opentip.prototype.debug = function() {
  var args;
  args = 1 <= arguments.length ? __slice.call(arguments, 0) : [];
  if (Opentip.debug && ((typeof console !== "undefined" && console !== null ? console.debug : void 0) != null)) {
    args.unshift("#" + this.id + " |");
    return console.debug.apply(console, args);
  }
};

Opentip.findElements = function() {
  var adapter, content, element, optionName, optionValue, options, _i, _len, _ref, _results;
  adapter = Opentip.adapter;
  _ref = adapter.findAll(document.body, "[data-ot]");
  _results = [];
  for (_i = 0, _len = _ref.length; _i < _len; _i++) {
    element = _ref[_i];
    options = {};
    content = adapter.data(element, "ot");
    if (content === "" || content === "true" || content === "yes") {
      content = adapter.attr(element, "title");
      adapter.attr(element, "title", "");
    }
    content = content || "";
    for (optionName in Opentip.styles.standard) {
      optionValue = adapter.data(element, "ot" + (Opentip.prototype.ucfirst(optionName)));
      if (optionValue != null) {
        if (optionValue === "yes" || optionValue === "true" || optionValue === "on") {
          optionValue = true;
        } else if (optionValue === "no" || optionValue === "false" || optionValue === "off") {
          optionValue = false;
        }
        options[optionName] = optionValue;
      }
    }
    _results.push(new Opentip(element, content, options));
  }
  return _results;
};

Opentip.version = "2.4.6";

Opentip.debug = false;

Opentip.lastId = 0;

Opentip.lastZIndex = 100;

Opentip.tips = [];

Opentip._abortShowingGroup = function(group, originatingOpentip) {
  var opentip, _i, _len, _ref, _results;
  _ref = Opentip.tips;
  _results = [];
  for (_i = 0, _len = _ref.length; _i < _len; _i++) {
    opentip = _ref[_i];
    if (opentip !== originatingOpentip && opentip.options.group === group) {
      _results.push(opentip._abortShowing());
    } else {
      _results.push(void 0);
    }
  }
  return _results;
};

Opentip._hideGroup = function(group, originatingOpentip) {
  var opentip, _i, _len, _ref, _results;
  _ref = Opentip.tips;
  _results = [];
  for (_i = 0, _len = _ref.length; _i < _len; _i++) {
    opentip = _ref[_i];
    if (opentip !== originatingOpentip && opentip.options.group === group) {
      _results.push(opentip.hide());
    } else {
      _results.push(void 0);
    }
  }
  return _results;
};

Opentip.adapters = {};

Opentip.adapter = null;

firstAdapter = true;

Opentip.addAdapter = function(adapter) {
  Opentip.adapters[adapter.name] = adapter;
  if (firstAdapter) {
    Opentip.adapter = adapter;
    adapter.domReady(Opentip.findElements);
    adapter.domReady(Opentip.followMousePosition);
    return firstAdapter = false;
  }
};

Opentip.positions = ["top", "topRight", "right", "bottomRight", "bottom", "bottomLeft", "left", "topLeft"];

Opentip.position = {};

_ref = Opentip.positions;
for (i = _i = 0, _len = _ref.length; _i < _len; i = ++_i) {
  position = _ref[i];
  Opentip.position[position] = i;
}

Opentip.styles = {
  standard: {
    "extends": null,
    title: void 0,
    escapeTitle: true,
    escapeContent: false,
    className: "standard",
    stem: true,
    delay: null,
    hideDelay: 0.1,
    fixed: false,
    showOn: "mouseover",
    hideTrigger: "trigger",
    hideTriggers: [],
    hideOn: null,
    removeElementsOnHide: false,
    offset: [0, 0],
    containInViewport: true,
    autoOffset: true,
    showEffect: "appear",
    hideEffect: "fade",
    showEffectDuration: 0.3,
    hideEffectDuration: 0.2,
    stemLength: 5,
    stemBase: 8,
    tipJoint: "top left",
    target: null,
    targetJoint: null,
    cache: true,
    ajax: false,
    ajaxMethod: "GET",
    ajaxErrorMessage: "There was a problem downloading the content.",
    group: null,
    style: null,
    background: "#fff18f",
    backgroundGradientHorizontal: false,
    closeButtonOffset: [5, 5],
    closeButtonRadius: 7,
    closeButtonCrossSize: 4,
    closeButtonCrossColor: "#d2c35b",
    closeButtonCrossLineWidth: 1.5,
    closeButtonLinkOverscan: 6,
    borderRadius: 5,
    borderWidth: 1,
    borderColor: "#f2e37b",
    shadow: true,
    shadowBlur: 10,
    shadowOffset: [3, 3],
    shadowColor: "rgba(0, 0, 0, 0.1)"
  },
  glass: {
    "extends": "standard",
    className: "glass",
    background: [[0, "rgba(252, 252, 252, 0.8)"], [0.5, "rgba(255, 255, 255, 0.8)"], [0.5, "rgba(250, 250, 250, 0.9)"], [1, "rgba(245, 245, 245, 0.9)"]],
    borderColor: "#eee",
    closeButtonCrossColor: "rgba(0, 0, 0, 0.2)",
    borderRadius: 15,
    closeButtonRadius: 10,
    closeButtonOffset: [8, 8]
  },
  dark: {
    "extends": "standard",
    className: "dark",
    borderRadius: 13,
    borderColor: "#444",
    closeButtonCrossColor: "rgba(240, 240, 240, 1)",
    shadowColor: "rgba(0, 0, 0, 0.3)",
    shadowOffset: [2, 2],
    background: [[0, "rgba(30, 30, 30, 0.7)"], [0.5, "rgba(30, 30, 30, 0.8)"], [0.5, "rgba(10, 10, 10, 0.8)"], [1, "rgba(10, 10, 10, 0.9)"]]
  },
  alert: {
    "extends": "standard",
    className: "alert",
    borderRadius: 1,
    borderColor: "#AE0D11",
    closeButtonCrossColor: "rgba(255, 255, 255, 1)",
    shadowColor: "rgba(0, 0, 0, 0.3)",
    shadowOffset: [2, 2],
    background: [[0, "rgba(203, 15, 19, 0.7)"], [0.5, "rgba(203, 15, 19, 0.8)"], [0.5, "rgba(189, 14, 18, 0.8)"], [1, "rgba(179, 14, 17, 0.9)"]]
  }
};

Opentip.defaultStyle = "standard";

if (typeof module !== "undefined" && module !== null) {
  module.exports = Opentip;
} else {
  window.Opentip = Opentip;
}


// Generated by CoffeeScript 1.4.0
var __slice = [].slice;

(function($) {
  var Adapter;
  $.fn.opentip = function(content, title, options) {
    return new Opentip(this, content, title, options);
  };
  Adapter = (function() {

    function Adapter() {}

    Adapter.prototype.name = "jquery";

    Adapter.prototype.domReady = function(callback) {
      return $(callback);
    };

    Adapter.prototype.create = function(html) {
      return $(html);
    };

    Adapter.prototype.wrap = function(element) {
      element = $(element);
      if (element.length > 1) {
        throw new Error("Multiple elements provided.");
      }
      return element;
    };

    Adapter.prototype.unwrap = function(element) {
      return $(element)[0];
    };

    Adapter.prototype.tagName = function(element) {
      return this.unwrap(element).tagName;
    };

    Adapter.prototype.attr = function() {
      var args, element, _ref;
      element = arguments[0], args = 2 <= arguments.length ? __slice.call(arguments, 1) : [];
      return (_ref = $(element)).attr.apply(_ref, args);
    };

    Adapter.prototype.data = function() {
      var args, element, _ref;
      element = arguments[0], args = 2 <= arguments.length ? __slice.call(arguments, 1) : [];
      return (_ref = $(element)).data.apply(_ref, args);
    };

    Adapter.prototype.find = function(element, selector) {
      return $(element).find(selector).get(0);
    };

    Adapter.prototype.findAll = function(element, selector) {
      return $(element).find(selector);
    };

    Adapter.prototype.update = function(element, content, escape) {
      element = $(element);
      if (escape) {
        return element.text(content);
      } else {
        return element.html(content);
      }
    };

    Adapter.prototype.append = function(element, child) {
      return $(element).append(child);
    };

    Adapter.prototype.remove = function(element) {
      return $(element).remove();
    };

    Adapter.prototype.addClass = function(element, className) {
      return $(element).addClass(className);
    };

    Adapter.prototype.removeClass = function(element, className) {
      return $(element).removeClass(className);
    };

    Adapter.prototype.css = function(element, properties) {
      return $(element).css(properties);
    };

    Adapter.prototype.dimensions = function(element) {
      return {
        width: $(element).outerWidth(),
        height: $(element).outerHeight()
      };
    };

    Adapter.prototype.scrollOffset = function() {
      return [window.pageXOffset || document.documentElement.scrollLeft || document.body.scrollLeft, window.pageYOffset || document.documentElement.scrollTop || document.body.scrollTop];
    };

    Adapter.prototype.viewportDimensions = function() {
      return {
        width: document.documentElement.clientWidth,
        height: document.documentElement.clientHeight
      };
    };

    Adapter.prototype.mousePosition = function(e) {
      if (e == null) {
        return null;
      }
      return {
        x: e.pageX,
        y: e.pageY
      };
    };

    Adapter.prototype.offset = function(element) {
      var offset;
      offset = $(element).offset();
      return {
        left: offset.left,
        top: offset.top
      };
    };

    Adapter.prototype.observe = function(element, eventName, observer) {
      return $(element).bind(eventName, observer);
    };

    Adapter.prototype.stopObserving = function(element, eventName, observer) {
      return $(element).unbind(eventName, observer);
    };

    Adapter.prototype.ajax = function(options) {
      var _ref, _ref1;
      if (options.url == null) {
        throw new Error("No url provided");
      }
      return $.ajax({
        url: options.url,
        type: (_ref = (_ref1 = options.method) != null ? _ref1.toUpperCase() : void 0) != null ? _ref : "GET"
      }).done(function(content) {
        return typeof options.onSuccess === "function" ? options.onSuccess(content) : void 0;
      }).fail(function(request) {
        return typeof options.onError === "function" ? options.onError("Server responded with status " + request.status) : void 0;
      }).always(function() {
        return typeof options.onComplete === "function" ? options.onComplete() : void 0;
      });
    };

    Adapter.prototype.clone = function(object) {
      return $.extend({}, object);
    };

    Adapter.prototype.extend = function() {
      var sources, target;
      target = arguments[0], sources = 2 <= arguments.length ? __slice.call(arguments, 1) : [];
      return $.extend.apply($, [target].concat(__slice.call(sources)));
    };

    return Adapter;

  })();
  return Opentip.addAdapter(new Adapter);
})(jQuery);

Opentip.styles.cdTooltipStyle = {
  extends: "dark",
  shadow: false,
  tipJoint: "top",
  borderRadius:2,
  offset:[0,-10],
  showEffectDuration:0,
  hideEffectDuration:0
};
Opentip.defaultStyle = "cdTooltipStyle";

/*
 * ! jQuery Smooth Scroll - v2.0.0 - 2016-07-31
 * https://github.com/kswedberg/jquery-smooth-scroll Copyright (c) 2016 Karl
 * Swedberg Licensed MIT
 */

(function(factory) {
  if (typeof define === 'function' && define.amd) {
    // AMD. Register as an anonymous module.
    define(['jquery'], factory);
  } else if (typeof module === 'object' && module.exports) {
    // CommonJS
    factory(require('jquery'));
  } else {
    // Browser globals
    factory(jQuery);
  }
}(function($) {

  var version = '2.0.0';
  var optionOverrides = {};
  var defaults = {
    exclude: [],
    excludeWithin: [],
    offset: 0,

    // one of 'top' or 'left'
    direction: 'top',

    // if set, bind click events through delegation
    // supported since jQuery 1.4.2
    delegateSelector: null,

    // jQuery set of elements you wish to scroll (for $.smoothScroll).
    // if null (default), $('html, body').firstScrollable() is used.
    scrollElement: null,

    // only use if you want to override default behavior
    scrollTarget: null,

    // fn(opts) function to be called before scrolling occurs.
    // `this` is the element(s) being scrolled
    beforeScroll: function() {},

    // fn(opts) function to be called after scrolling occurs.
    // `this` is the triggering element
    afterScroll: function() {},

    // easing name. jQuery comes with "swing" and "linear." For others, you'll
	// need an easing plugin
    // from jQuery UI or elsewhere
    easing: 'swing',

    // speed can be a number or 'auto'
    // if 'auto', the speed will be calculated based on the formula:
    // (current scroll position - target scroll position) / autoCoeffic
    speed: 400,

    // coefficient for "auto" speed
    autoCoefficient: 2,

    // $.fn.smoothScroll only: whether to prevent the default click action
    preventDefault: true
  };

  var getScrollable = function(opts) {
    var scrollable = [];
    var scrolled = false;
    var dir = opts.dir && opts.dir === 'left' ? 'scrollLeft' : 'scrollTop';

    this.each(function() {
      var el = $(this);

      if (this === document || this === window) {
        return;
      }

      if (document.scrollingElement && (this === document.documentElement || this === document.body)) {
        scrollable.push(document.scrollingElement);

        return false;
      }

      if (el[dir]() > 0) {
        scrollable.push(this);
      } else {
        // if scroll(Top|Left) === 0, nudge the element 1px and see if it moves
        el[dir](1);
        scrolled = el[dir]() > 0;

        if (scrolled) {
          scrollable.push(this);
        }
        // then put it back, of course
        el[dir](0);
      }
    });

    if (!scrollable.length) {
      this.each(function() {
        // If no scrollable elements and <html> has scroll-behavior:smooth
		// because
        // "When this property is specified on the root element, it applies to
		// the viewport instead."
        // and "The scroll-behavior property of the … body element is *not*
		// propagated to the viewport."
        // → https://drafts.csswg.org/cssom-view/#propdef-scroll-behavior
        if (this === document.documentElement && $(this).css('scrollBehavior') === 'smooth') {
          scrollable = [this];
        }

        // If still no scrollable elements, fall back to <body>,
        // if it's in the jQuery collection
        // (doing this because Safari sets scrollTop async,
        // so can't set it to 1 and immediately get the value.)
        if (!scrollable.length && this.nodeName === 'BODY') {
          scrollable = [this];
        }
      });
    }

    // Use the first scrollable element if we're calling firstScrollable()
    if (opts.el === 'first' && scrollable.length > 1) {
      scrollable = [scrollable[0]];
    }

    return scrollable;
  };

  $.fn.extend({
    scrollable: function(dir) {
      var scrl = getScrollable.call(this, {dir: dir});

      return this.pushStack(scrl);
    },
    firstScrollable: function(dir) {
      var scrl = getScrollable.call(this, {el: 'first', dir: dir});

      return this.pushStack(scrl);
    },

    smoothScroll: function(options, extra) {
      options = options || {};

      if (options === 'options') {
        if (!extra) {
          return this.first().data('ssOpts');
        }

        return this.each(function() {
          var $this = $(this);
          var opts = $.extend($this.data('ssOpts') || {}, extra);

          $(this).data('ssOpts', opts);
        });
      }

      var opts = $.extend({}, $.fn.smoothScroll.defaults, options);

      var clickHandler = function(event) {
        var escapeSelector = function(str) {
          return str.replace(/(:|\.|\/)/g, '\\$1');
        };

        var link = this;
        var $link = $(this);
        var thisOpts = $.extend({}, opts, $link.data('ssOpts') || {});
        var exclude = opts.exclude;
        var excludeWithin = thisOpts.excludeWithin;
        var elCounter = 0;
        var ewlCounter = 0;
        var include = true;
        var clickOpts = {};
        var locationPath = $.smoothScroll.filterPath(location.pathname);
        var linkPath = $.smoothScroll.filterPath(link.pathname);
        var hostMatch = location.hostname === link.hostname || !link.hostname;
        var pathMatch = thisOpts.scrollTarget || (linkPath === locationPath);
        var thisHash = escapeSelector(link.hash);

        if (thisHash && !$(thisHash).length) {
          include = false;
        }

        if (!thisOpts.scrollTarget && (!hostMatch || !pathMatch || !thisHash)) {
          include = false;
        } else {
          while (include && elCounter < exclude.length) {
            if ($link.is(escapeSelector(exclude[elCounter++]))) {
              include = false;
            }
          }

          while (include && ewlCounter < excludeWithin.length) {
            if ($link.closest(excludeWithin[ewlCounter++]).length) {
              include = false;
            }
          }
        }

        if (include) {
          if (thisOpts.preventDefault) {
            event.preventDefault();
          }

          $.extend(clickOpts, thisOpts, {
            scrollTarget: thisOpts.scrollTarget || thisHash,
            link: link
          });

          $.smoothScroll(clickOpts);
        }
      };

      if (options.delegateSelector !== null) {
        this
        .off('click.smoothscroll', options.delegateSelector)
        .on('click.smoothscroll', options.delegateSelector, clickHandler);
      } else {
        this
        .off('click.smoothscroll')
        .on('click.smoothscroll', clickHandler);
      }

      return this;
    }
  });

  $.smoothScroll = function(options, px) {
    if (options === 'options' && typeof px === 'object') {
      return $.extend(optionOverrides, px);
    }
    var opts, $scroller, scrollTargetOffset, speed, delta;
    var scrollerOffset = 0;
    var offPos = 'offset';
    var scrollDir = 'scrollTop';
    var aniProps = {};
    var aniOpts = {};

    if (typeof options === 'number') {
      opts = $.extend({link: null}, $.fn.smoothScroll.defaults, optionOverrides);
      scrollTargetOffset = options;
    } else {
      opts = $.extend({link: null}, $.fn.smoothScroll.defaults, options || {}, optionOverrides);

      if (opts.scrollElement) {
        offPos = 'position';

        if (opts.scrollElement.css('position') === 'static') {
          opts.scrollElement.css('position', 'relative');
        }
      }
    }

    scrollDir = opts.direction === 'left' ? 'scrollLeft' : scrollDir;

    if (opts.scrollElement) {
      $scroller = opts.scrollElement;

      if (!(/^(?:HTML|BODY)$/).test($scroller[0].nodeName)) {
        scrollerOffset = $scroller[scrollDir]();
      }
    } else {
      $scroller = $('html, body').firstScrollable(opts.direction);
    }

    // beforeScroll callback function must fire before calculating offset
    opts.beforeScroll.call($scroller, opts);

    scrollTargetOffset = (typeof options === 'number') ? options :
                          px ||
                          ($(opts.scrollTarget)[offPos]() &&
                          $(opts.scrollTarget)[offPos]()[opts.direction]) ||
                          0;

    aniProps[scrollDir] = scrollTargetOffset + scrollerOffset + opts.offset;
    speed = opts.speed;

    // automatically calculate the speed of the scroll based on distance /
	// coefficient
    if (speed === 'auto') {

      // $scroller[scrollDir]() is position before scroll, aniProps[scrollDir]
		// is position after
      // When delta is greater, speed will be greater.
      delta = Math.abs(aniProps[scrollDir] - $scroller[scrollDir]());

      // Divide the delta by the coefficient
      speed = delta / opts.autoCoefficient;
    }

    aniOpts = {
      duration: speed,
      easing: opts.easing,
      complete: function() {
        opts.afterScroll.call(opts.link, opts);
      }
    };

    if (opts.step) {
      aniOpts.step = opts.step;
    }

    if ($scroller.length) {
      $scroller.stop().animate(aniProps, aniOpts);
    } else {
      opts.afterScroll.call(opts.link, opts);
    }
  };

  $.smoothScroll.version = version;
  $.smoothScroll.filterPath = function(string) {
    string = string || '';

    return string
      .replace(/^\//, '')
      .replace(/(?:index|default).[a-zA-Z]{3,4}$/, '')
      .replace(/\/$/, '');
  };

  // default options
  $.fn.smoothScroll.defaults = defaults;

}));

$(function() {
	var PROJECT_OBJECT = 'CurrenciesDirect';
	window[PROJECT_OBJECT] = new Object();
/* Util */

window[PROJECT_OBJECT].Util = {

	setMaterialIcon: function(elem,icon) {

		var element = $(elem),
			newIcon = icon;

		if (!$(element).hasClass('material-icons')) {
			element = $(element).find('.material-icons').get(0);
		} else {
			$(element).text(newIcon);
		}

		$(element).text(newIcon);

	},

	toggleMoreIcon: function(icon,expanding) {

		var theIcon		= icon,
			isExpanding = expanding;

		if (isExpanding) {
			$(theIcon).addClass('material-icons--open');
		} else {
			$(theIcon).removeClass('material-icons--open');
		}

	},

	bindEscapeKey: function() {
		$(document).keyup(function(e) {
		     if (e.keyCode == 27) {
		        window[PROJECT_OBJECT].Drawer.closeCurrentDrawer();
		        window[PROJECT_OBJECT].Modal.close();
		        window[PROJECT_OBJECT].Clickpanel.closeAll();
		    }
		});
	},

	removePageloads:function() {
		$('.page-load').removeClass('page-load');
	},

	initSmoothScrolls:function() {
		$('.accordion-trigger,a[href^="#"]').smoothScroll();
	},

	scrollToTop:function() {
		$('html,body').animate({scrollTop:$('body').offset().top},250);
	},

	initDevNotes:function() {

		$('#dev-notes').draggable();

		$(document).on('click','.dev-notes__close',function() {
			$('#dev-notes').addClass('dev-notes--minimised').removeClass('dev-notes--maximised');
		});

		$(document).on('click','.dev-notes--minimised',function() {
			$('#dev-notes').removeClass('dev-notes--minimised').addClass('dev-notes--maximised');
		});

	},

	tickButton:function(b) {

		var theButton 	= b,
			theText		= $(theButton).text(),
			theClasses  = $(theButton).attr('class');

			// theTip = new Opentip(theButton,theText, { tipJoint: "bottom" });

		$(theButton).data('original-text',theText);
		$(theButton).data('original-classes',theClasses);

		$(theButton).html('<i class="material-icons">check</i>').removeClass($(theButton).data('original-classes')).addClass('button--positive accept-learn__btn-accept');

		if (theClasses.indexOf('button--small')>-1) {
			$(theButton).addClass('button--small');
		}

	},

	untickButton:function(b) {

		var theButton 	= b,
			theText 	= $(theButton).data('original-text'),
			theClasses 	= $(theButton).data('original-classes');

		$(theButton).html(theText).removeClass('button--positive').addClass(theClasses);

	},

	enableElement:function(e) {

		var theElement = e;

		if ($(theElement).is('input')) {
			$(theElement).prop('disabled',false);
		}

		if ($(theElement).hasClass('button--primary') || $(theElement).hasClass('button--secondary') || $(theElement).hasClass('button--postive') || $(theElement).hasClass('button--negative') || $(theElement).hasClass('button--neutral')) {
			$(theElement).removeClass('button--disabled');
		}

	},

	disableElement:function(e) {

		var theElement = e;

		if ($(theElement).is('input')) {
			$(theElement).prop('disabled',true);
		}

		if ($(theElement).hasClass('button--primary') || $(theElement).hasClass('button--secondary') || $(theElement).hasClass('button--postive') || $(theElement).hasClass('button--negative') || $(theElement).hasClass('button--neutral')) {
			$(theElement).addClass('button--disabled');
		}

	}

};
window[PROJECT_OBJECT].Navigation = {

	navItemsTruncated : [],

	init:function() {

		console.log('.Navigation.init()');

		var self = this;
		self.tooltipTruncated();

		$(document).on('click','.burger--minimise',function() {
			self.minimise();
		});

		$(document).on('click','.burger--maximise',function() {
			self.maximise();
		});

		$(document).on('click','#main-nav > ul > li > a[href="#"]',function(e) {
			e.preventDefault();
		});

		$('.main-nav__item,.main-nav__item--on').has('.main-nav__sub-nav').addClass('main-nav__item--has-sub').find('.main-nav__icon').append('<span class="main-nav__more-arrow"></span>');

	},

	minimise:function() {

		var self = this;

		$('.burger--minimise').removeClass('burger--minimise').addClass('burger--maximise');
		$('#main-nav').removeClass('main-nav--maximised').addClass('main-nav--minimised');
		$('#main-content').removeClass('main-content--small').addClass('main-content--large');
		$('.main-nav__text').removeClass('main-nav__text').addClass('main-nav__text--hidden');
		$('#slip .ui-resizable-n').removeClass('shift');

		self.deactivateTooltips();
		self.cleanupPostNav();

	},

	maximise:function() {

		var self = this;

		if (!self.beenOpened) {
			self.beenOpened = true;
		}

		$('.burger--maximise').removeClass('burger--maximise').addClass('burger--minimise');
		$('#main-nav').removeClass('main-nav--minimised').addClass('main-nav--maximised');
		$('#main-content').removeClass('main-content--large').addClass('main-content--small');
		$('.main-nav__text--hidden').removeClass('main-nav__text--hidden').addClass('main-nav__text');
		$('#slip .ui-resizable-n').addClass('shift');

		self.waveSubnav();
		self.activateTooltips();
		self.cleanupPostNav();

	},

	isOpen:function() {
		return $('.main-nav--maximised').length;
	},

	beenOpened : false,

	hasBeenOpenedOnce:function() {
		var self = this;
		return self.beenOpened;
	},

	tooltipTruncated:function() {

		var self = this;

		$('.main-nav__text, .sub-nav__item a').each(function() {

			if ($(this)[0].scrollWidth > $(this).innerWidth()) {
				var ot = new Opentip($(this),$(this).text());
				self.navItemsTruncated.push(ot);
			}

		});

	},

	waveSubnav:function() {

		$('.sub-nav__item').addClass('sub-nav__item--wave-off');

		$('.main-nav__sub-nav').each(function() {

			var subNavItems 		= $(this).find('.sub-nav__item'),
				waveTimeIncrement 	= 25,
				waveTime 		  	= waveTimeIncrement;

			$(subNavItems).each(function() {

				var subNavItem = $(this);

				setTimeout(function() {
					$(subNavItem).removeClass('sub-nav__item--wave-off');
				},waveTime);

				waveTime += waveTimeIncrement;

			});

		});

	},

	activateTooltips:function() {

		var self = this;

		if (self.navItemsTruncated.length) {
			for(var i=0;i<self.navItemsTruncated.length;i++) {
				self.navItemsTruncated[i].activate();
			}
		}
	},

	deactivateTooltips:function() {

		var self = this;

		if (self.navItemsTruncated.length) {
			for(var i=0;i<self.navItemsTruncated.length;i++) {
				self.navItemsTruncated[i].deactivate();
			}
		}
	},

	cleanupPostNav:function() {
		setTimeout(function() {

			/*
			 * if ($('.table-fix').length) {
			 * window[PROJECT_OBJECT].Tables.sizeFixedHeaders();
			 * window[PROJECT_OBJECT].Tables.leftifyFixedHeaders(); }
			 */

			if ($('.scrolltable').length) {
				window[PROJECT_OBJECT].Tables.initScrolltable();
			}

			window[PROJECT_OBJECT].Slip.adjust();

			/*
			 * window[PROJECT_OBJECT].Tables.sizeFixedHeaders();
			 * window[PROJECT_OBJECT].Tables.leftifyFixedHeaders();
			 * window[PROJECT_OBJECT].Tables.leftifyScrollableHeaders();
			 * window[PROJECT_OBJECT].Tables.rightifyFilterActions();
			 * window[PROJECT_OBJECT].Tables.moveScrolltableFixedCols();
			 */
			window[PROJECT_OBJECT].Tooltips.hideAll();
		}, 200);
	}

};
/* Drawer */

window[PROJECT_OBJECT].Drawer = {

	init:function() {

		console.log('.Drawer.init()');

		var self = this;

		$(document).on('click','.drawer-trigger,.drawer-trigger--active',function(e) {

			var theDrawerTrigger 	= $(this),
				theDrawer 			= $('#'+$(theDrawerTrigger).data('drawer'));

			if ($(theDrawer).hasClass('drawer--open')) {
				self.close(theDrawerTrigger,theDrawer);
			} else {
				self.open(theDrawerTrigger,theDrawer);
			}

			e.preventDefault();

		});

		$(document).on('click','.drawer__close',function() {

			var theCloseTrigger 	= $(this),
				theDrawer 			= $(theCloseTrigger).closest('.drawer--open'),
				theDrawerTrigger 	= $('.drawer-trigger--active[data-drawer="'+$(theDrawer).attr('id')+'"]');

			self.close(theDrawerTrigger,theDrawer);

		});

		$(document).on('click','#main-content',function() {
			self.closeCurrentDrawer();
		});

	},

	open:function(t,d) {

		var self 		= this,
			theTrigger 	= t,
			theDrawer 	= d;

		self.closeCurrentDrawer();
		$(theTrigger).removeClass('drawer-trigger').addClass('drawer-trigger--active');
		$(theDrawer).removeClass('drawer--closed').addClass('drawer--open');

	},

	close:function(t,d) {

		var theTrigger 	= t,
			theDrawer 	= d;

		$(theTrigger).removeClass('drawer-trigger--active').addClass('drawer-trigger');
		$(theDrawer).removeClass('drawer--open').addClass('drawer--closed');

	},

	closeCurrentDrawer:function() {
		$('.drawer-trigger--active').addClass('drawer-trigger').removeClass('drawer-trigger--active');
		$('.drawer--open').addClass('drawer--closed').removeClass('drawer--open');
	}

};

window[PROJECT_OBJECT].AcceptLearn = {

	init:function() {

		console.log('.AcceptLearn.init()');

		var self = this;

		$(document).on('click', '.accept-learn__btn-accept:not(.button--disabled)', function(e) {

			var theAcceptLearn = $(this).closest('.accept-learn');

			if ($(this).find('.material-icons').length) {
				self.unacceptIt(theAcceptLearn);
			} else {
				self.acceptIt(theAcceptLearn);
			}

			e.preventDefault();

		});

	},

	acceptIt:function(a) {

		var self 			= this,
			theAcceptLearn 	= a,
			theAcceptInput	= $(theAcceptLearn).find('.accept-learn__text,.accept-learn__text--full'),
			theBtn 			= $(theAcceptLearn).find('.accept-learn__btn-accept');

		self.acceptSuggestion(theAcceptInput);
		window[PROJECT_OBJECT].Util.tickButton($(theBtn));

	},

	unacceptIt:function(a) {

		var self 			= this,
			theAcceptLearn 	= a,
			theAcceptInput	= $(theAcceptLearn).find('.accept-learn__text,.accept-learn__text--full'),
			theBtn 			= $(theAcceptLearn).find('.accept-learn__btn-accept');

		self.unacceptSuggestion(theAcceptInput);

		window[PROJECT_OBJECT].Util.untickButton($(theBtn));

	},

	acceptSuggestion:function(i) {

		var theInput = i;

		$(theInput).val($(theInput).attr('placeholder'));

	},

	unacceptSuggestion:function(i) {

		var theInput = i;

		$(theInput).attr('placeholder',$(theInput).val()).val('');

	}

}
window[PROJECT_OBJECT].Accordions = {

	init:function() {

		console.log('.Accordions.init()');

		var self = this;

		$(document).on('click','.accordion__header a',function(e) {

			var sectionTrigger 	= $(this),
				section 	 	= $(sectionTrigger).parent().parent(),
				sectionHeader	= $(section).find('.accordion__header').get(0),
				sectionContent	= $(section).find('.accordion__content').get(0);

			if (!$(sectionContent).hasClass('accordion__content--open')) {
				self.expandSection(section);
			} else {
				self.contractSection(sectionHeader,sectionContent);
			}

			e.preventDefault();

		});

		$(document).on('click','.accordion--quick-controls .quick-control__control--open-all',function(e) {
			self.expandAllSections($(this));
			e.preventDefault();
		});

		$(document).on('click','.accordion--quick-controls .quick-control__control--close-all',function(e) {
			self.contractAllSections($(this));
			e.preventDefault();
		});

		$(document).on('click','.accordion-trigger',function() {

			var theSection = $('#'+$(this).data('accordion-section'));

			self.expandSection(theSection);

		});

	},

	expandSection:function(s) {

		var theSection 			= s,
			theSectionHeader	= $(theSection).find('.accordion__header').get(0),
			theSectionContent	= $(theSection).find('.accordion__content').get(0),
			theIcon 			= $(theSectionHeader).find('.material-icons').get(0);

		$(theSectionHeader).addClass('accordion__header--open');
		$(theSectionContent).addClass('accordion__content--open');
		window[PROJECT_OBJECT].Util.toggleMoreIcon(theIcon,true);

	},

	contractSection:function(sh,sc) {

		var theSectionHeader	= sh,
			theSectionContent	= sc,
			theIcon 			= $(theSectionHeader).find('.material-icons').get(0);

		$(theSectionHeader).removeClass('accordion__header--open');
		$(theSectionContent).removeClass('accordion__content--open');
		window[PROJECT_OBJECT].Util.toggleMoreIcon(theIcon,false);

	},

	expandAllSections:function(qc) {

		var theQuickControl = qc,
			theAccordion 	= $(theQuickControl).closest('.accordion--quick-controls');

		$(theAccordion).find('> .accordion__section > .accordion__header').addClass('accordion__header--open');
		$(theAccordion).find('> .accordion__section > .accordion__content').addClass('accordion__content--open');
		window[PROJECT_OBJECT].Util.toggleMoreIcon($(theAccordion).find('> .accordion__section > .accordion__header .material-icons'),true);

	},

	contractAllSections:function(qc) {

		var theQuickControl = qc,
			theAccordion 	= $(theQuickControl).closest('.accordion--quick-controls');

		$(theAccordion).find('> .accordion__section > .accordion__header').removeClass('accordion__header--open');
		$(theAccordion).find('> .accordion__section > .accordion__content').removeClass('accordion__content--open');
		window[PROJECT_OBJECT].Util.toggleMoreIcon($(theAccordion).find('> .accordion__section > .accordion__header .material-icons'),false);

	}

};
window[PROJECT_OBJECT].Annex = {

	isScroller:$('.grid-annex-main--scroller').length,

	init:function() {

		console.log('.Annex.init()');

		var self = this;

		$(document).on('click', '.annex-trigger', function(e) {

			if (self.isClosed()) {
				self.open();
			} else {
				self.close();
			}

			e.preventDefault();

		});

		if (!self.isClosed()) {
			$('.annex-tab').addClass('hidden');
		}

	},


	isClosed:function() {
		return $('.grid-annex-side').hasClass('hidden');
	},

	open:function() {

		var self = this;
		$('.grid-annex-main,.grid-annex-main--scroller').removeClass('grid__col--12').addClass('grid__col--8');
		$('.grid-annex-side').removeClass('hidden');
		$('.annex').addClass('annex--visible').removeClass('annex--hidden');
		$('.annex-tab').addClass('hidden');

		if (self.isScroller) {
			$('.grid-annex-main').removeClass('grid-annex-main').addClass('grid-annex-main--scroller');
		}

		window[PROJECT_OBJECT].Tables.sizeFixedHeaders();

		if ($('.scrolltable').length) {
			window[PROJECT_OBJECT].Tables.initScrolltable();
		}

	},

	close:function() {

		var self = this;
		$('.grid-annex-main,.grid-annex-main--scroller').addClass('grid__col--12').removeClass('grid__col--8');
		$('.grid-annex-side').addClass('hidden');
		$('.annex').removeClass('annex--visible').addClass('annex--hidden');
		$('.annex-tab').removeClass('hidden');

		if (self.isScroller) {
			$('.grid-annex-main--scroller').removeClass('grid-annex-main--scroller').addClass('grid-annex-main');
		}

		window[PROJECT_OBJECT].Tables.sizeFixedHeaders();

		if ($('.scrolltable').length) {
			window[PROJECT_OBJECT].Tables.initScrolltable();
		}

	},

	openOnLoad:function() {
		var self = this;
		self.open();
	}

};
window[PROJECT_OBJECT].Clickpanel = {

	init:function() {

		var self = this,
			openPanel;

		$(document).on('click', '.clickpanel__drop-arrow, .clickpanel__trigger', function() {

			var theClickpanel 			= $(this).closest('.clickpanel, .clickpanel--left, .clickpanel--right'),
				theContentHidden		= $(theClickpanel).find('div[class^="clickpanel__content"]').first();

				if ($(theContentHidden).hasClass('clickpanel__content--hidden')) {
					self.closeAll();
					self.show(theContentHidden);
					openPanel = theClickpanel;
				} else {
					self.hide(theContentHidden);
				}

		});

		$(document).on('click',function() {

			if(!$(event.target).is($(openPanel)) && !$(event.target).is($(openPanel).find('*'))) {
				self.closeAll();
			}

		});

	},

	show:function(c) {

		var theContent = c;
		$(theContent).removeClass('clickpanel__content--hidden').addClass('clickpanel__content--visible');

		if ($(theContent).find('.multilist__search').length) {
			$(theContent).find('.multilist__search').focus();
		}

		if ($(theContent).find('.singlelist__search').length) {
			$(theContent).find('.singlelist__search').focus();
		}

	},

	hide:function(c) {

		var theContent = c;
		$(theContent).removeClass('clickpanel__content--visible').addClass('clickpanel__content--hidden');

	},

	closeAll:function() {
		$('.clickpanel__content--visible').removeClass('clickpanel__content--visible').addClass('clickpanel__content--hidden');
	}

};

window[PROJECT_OBJECT].Expand = {

	init:function() {

		console.log('.Expand.init()');

		var self = this;

		$(document).on('click','.expand__all-link',function() {

			var expandClass = $(this).data('expand');

			if ($('.'+expandClass).hasClass('hidden')) {
				self.expandAll($(this));
			} else {
				self.contractAll($(this));
			}

		});

	},

	expandAll:function(t) {

		var trigger 		= t,
			expandClass 	= $(trigger).data('expand'),
			contractText	= $(trigger).data('contract-text');

		$('.'+expandClass).removeClass('hidden');
		$(trigger).text(contractText);

	},

	contractAll:function(t) {

		var trigger 		= t,
			expandClass 	= $(trigger).data('expand'),
			expandText		= $(trigger).data('expand-text');

		$('.'+expandClass).addClass('hidden');
		$(trigger).text(expandText);

	}

};

window[PROJECT_OBJECT].Filters = {

	init:function() {

		console.log('.Filters.init()');

		var self = this;

		$(document).on('click','.filter-minimised',function() {

			if ($('.main-content__side').hasClass('hidden')) {
				self.show();
			} else {
				self.hide();
			}

		});

		$(document).on('click','.main-content__close-side',function() {
			self.hide();
		});

	},

	show:function() {
		$('.main-content__side').removeClass('hidden');
		$('.filter-minimised').addClass('hidden');
		$('.main-content__main').removeClass('large').addClass('small');
	},

	hide:function() {
		$('.main-content__side').addClass('hidden');
		$('.filter-minimised').removeClass('hidden');
		$('.main-content__main').removeClass('small').addClass('large');
	}

};

window[PROJECT_OBJECT].FilterLists = {

	init:function() {

		console.log('.Filterlists.init()');

	}

};
window[PROJECT_OBJECT].Forms = {

	init:function() {

		console.log('.Forms.init()');

		var self = this;

		self.initDatePickers();
		self.watchUKSortCodes();

		$(document).on('click','.button--reset-form',function(e) {

			var theForm = $(this).closest('form');

			self.resetForm($(theForm).attr('id'));
			e.preventDefault();

		});

	},

	initDatePickers:function() {

		$(document).on('focus', '.datepicker', function() {
			$('.datepicker').datepicker({ showOn:'focus', dateFormat: 'dd/mm/yy' });
        });

	},

	resetForm:function(f) {

		var theForm = $('#'+f);

		/* Reset text and date fields */
		$(theForm).find('input[type="text"]').val('');

		/* Reset pill choices, mulitlists and singlelists */
		$(theForm).find('.pill-choice, .pill-choice--small, .multilist, .singlelist').find('input[type="checkbox"], input[type="radio"]').prop('checked', false).trigger('change');

	},

	watchUKSortCodes:function() {

		$(document).on('blur','.uk-sort-code',function() {

		    var thisVal = $(this).val(),
		    	newVal	= thisVal;

		    if (/^(\d{2}\s\d{2}\s\d{2})$/.test(thisVal)) {
		    	newVal = thisVal.replace(/[\s]+/g,'-');
		    }

		    if (/^\d{6}$/.test(thisVal)) {
		    	newVal = thisVal.match(/\d{1,2}/g).join('-');
		    }

			$(this).val(newVal);

		});

	}

};
window[PROJECT_OBJECT].Hoverpanels = {

	init:function() {

		console.log('.Hoverpanels.init()');

		var self = this;

		$(document).on('mouseenter','.hoverpanel__trigger',function() {
			var hoverpanelContent = $(this).siblings('.hoverpanel__content');
			self.show(hoverpanelContent);
		});

		/*
		 * $(document).on('mouseleave','.hoverpanel',function() { var
		 * hoverpanelContent = $(this).children('.hoverpanel__content');
		 * self.hide(hoverpanelContent); });
		 */

	},

	show:function(c) {
		$(c).addClass('hoverpanel__content--show');
	},

	hide:function(c) {
		$(c).removeClass('hoverpanel__content--show');
	}

};
window[PROJECT_OBJECT].InputMores = {

	init:function() {

		console.log('.InputMores.init()');

		var self = this;

		self.showOnPageload();

		$(document).on('change','.input-more',function() {

			var theInput = $(this);

			self.showArea(theInput);

		});

		$(document).on('change','.input-more-hide',function() {

			var theInput = $(this);

			self.hideAreas(theInput);

		});

	},

	showArea:function(i) {

		console.log('showing');

		var theInput 		= $(i),
			theInputType	= $(theInput).attr('type'),
			theMoreArea		= $('#'+$(theInput).data('more-area'));

		if (theInputType == "radio") {

			if ($(theMoreArea).parent().parent().hasClass('form__field-wrap--empty')) {
				$(theMoreArea).parent().parent().removeClass('form__field-wrap--empty');
			}
			$(theMoreArea).siblings().addClass('input-more-areas__area--hidden').removeClass('input-more-areas__area');
			$(theMoreArea).removeClass('input-more-areas__area--hidden').addClass('input-more-areas__area');

		}

	},

	hideArea:function(i) {

		var theInput 		= $(i),
			theInputType	= $(theInput).attr('type'),
			theMoreArea		= $('#'+$(theInput).data('more-area'));

		if (theInputType == "radio") {

			if ($(theMoreArea).parent().parent().hasClass('form__field-wrap--empty')) {
				$(theMoreArea).parent().parent().removeClass('form__field-wrap--empty');
			}
			$(theMoreArea).removeClass('input-more-areas__area').addClass('input-more-areas__area--hidden');

		}

	},

	hideAreas:function(i) {

		var theInput 		= $(i),
			theInputType	= $(theInput).attr('type'),
			theMoreArea		= $('#'+$(theInput).data('more-hide'));

		if (theInputType == "radio") {

			if ($(theInput).is(':checked')) {

				$(theMoreArea).find('.input-more-areas__area').addClass('input-more-areas__area--hidden').removeClass('input-more-areas__area');
				/*
				 * if ($(theMoreArea).parent().hasClass('form__field-wrap')) {
				 * $(theMoreArea).parent().addClass('form__field-wrap--empty'); }
				 */
			}

		}

	},

	showOnPageload:function() {

		$('input[type="radio"].input-more:checked').each(function() {
			$('#'+$(this).data('more-area')).removeClass('input-more-areas__area--hidden').addClass('input-more-areas__area');
		});

	}

};
window[PROJECT_OBJECT].Modal = {

	init:function() {

		var self = this;

		$(document).on('click','.modal-trigger:not(.button--disabled)',function(e) {

			var theModalTrigger = $(this),
				theModal 		= $('#'+$(this).data('modal'));

			self.open(theModal);
			e.preventDefault();

		});

		// $(document).on('click','#modal-mask, .modal__close,
		// .modal__close-x',function(e) {
		$(document).on('click','.modal__close, .modal__close-x',function(e) {
			self.close();
			e.preventDefault();
		});

	},

	open:function(m) {

		var theModalMask 	= $('#modal-mask'),
			theModal 		= m;

		if (!$(theModalMask).length) {
			$('body').append('<div id="modal-mask" class="modal-mask--visible"></div>');
		} else {
			$(theModalMask).removeClass('modal-mask--hidden').addClass('modal-mask--visible');
		}

		$(theModal).addClass('modal--visible').removeClass('modal--hidden');

	},

	close:function() {

		var theModalMask = $('#modal-mask');
		$(theModalMask).addClass('modal-mask--hidden').removeClass('modal-mask--visible');
		$('.modal--visible').addClass('modal--hidden').removeClass('modal--visible');

	},

	justAMoment:function() {

		var self = this;
		self.close();
		self.open($('#modal-global-just-a-moment'));

	}

};

window[PROJECT_OBJECT].Multilist = {

	init:function() {

		var self = this;

		$('.multilist').each(function() {

			var theMultilist 	= $(this),
				theChecked	 	= $(theMultilist).find('input[type="checkbox"]:checked'),
				theCheckedIds	= [];

			if (!$(theChecked).length) {
				theMultilist.data('chosen-options',[]);
			} else {
				$(theChecked).each(function() {
					var theChecked = $(this);
					theCheckedIds.push(theChecked.attr('id'));
				});
				theMultilist.data('chosen-options',theCheckedIds);
				self.updateChosenPills(theMultilist);
			}

		});

		$(document).on('change','.multilist__options input[type="checkbox"], .multilist__options--scrollable input[type="checkbox"]',function() {

			var theOptionId		= $(this).attr('id'),
				theOption 		= $('#'+theOptionId),
				theMultilist 	= $(theOption).closest('.multilist');

			if ($(theOption).is(':checked')) {
				self.addChosen(theOptionId);
			} else {
				self.removeChosen(theOptionId);
			}

		});

		$(document).on('click','.multilist__pill',function(e) {

			var thePill 	= $(this);

			self.removeChosen($(thePill).data('id-to-remove'));
			e.preventDefault();

		});

		$(document).on('keyup search','.multilist__search',function() {
			self.searchList($(this).val(),$(this).siblings('.multilist__options,.multilist__options--scrollable'));
		});

	},

	addChosen:function(id) {

		var self			= this,
			theOptionId 	= id,
			theOption 		= $('#'+theOptionId),
			theMultilist 	= $(theOption).closest('.multilist'),
			theChosenOnes	= $(theMultilist).data('chosen-options');

			theChosenOnes.push(theOptionId);
			$(theMultilist).data('chosen-options',theChosenOnes);
			$(theOption).prop('checked',true);
			self.updateChosenPills(theMultilist);

	},

	removeChosen:function(id) {

		var self			= this,
			theOptionId 	= id,
			theOption 		= $('#'+theOptionId),
			theMultilist 	= $(theOption).closest('.multilist'),
			theChosenOnes	= $(theMultilist).data('chosen-options');

			theChosenOnes.splice(theChosenOnes.indexOf(theOptionId),1);
			$(theMultilist).data('chosen-options',theChosenOnes);
			$(theOption).prop('checked',false);
			self.updateChosenPills(theMultilist);

	},

	updateChosenPills:function(m) {

		var theMultilist 	= $(m),
			theChosenOnes	= $(theMultilist).data('chosen-options'),
			theChosenList	= $(theMultilist).find('.multilist__chosen');

		$(theChosenList).children('li:not(:first)').remove();

		for (var c=0; c < theChosenOnes.length; c++) {
			$(theChosenList).append('<li><a class="multilist__pill removeable-pill" href="#" data-id-to-remove="'+theChosenOnes[c]+'">'+$('label[for="'+theChosenOnes[c]+'"]').text()+'<i class="material-icons">close</i></a></li>');
		}

		if (!theChosenOnes.length) {
			$(theChosenList).children('li:first').removeClass('hidden');
		} else {
			$(theChosenList).children('li:first').addClass('hidden');
		}

	},

	searchList:function(s,l) {

		var theSearchString = s.toLowerCase(),
			theList 		= l,
			theListItems	= $(theList).find('li'),
			matchPattern	= new RegExp(theSearchString);

		if (!theSearchString.length) {
			$(theListItems).removeClass('hidden');
			return;
		}

		$(theListItems).each(function() {

			var theListItem 	= $(this),
				theLabelText 	= $(theListItem).find('label').text().trim().toLowerCase();

			if (matchPattern.test(theLabelText)) {
				$(theListItem).removeClass('hidden');
			} else {
				$(theListItem).addClass('hidden');
			}

		});

	}

};

window[PROJECT_OBJECT].PageSwitcher = {

	init:function() {

		var self = this;

		$(document).on('click','.page-switcher__page',function(e) {

			self.hide();
			self.show($(this));


		});

	},

	show:function(p) {

		var thePageSwitcher = p,
			thePage = $('#'+$(thePageSwitcher).data('page'));

		$(thePage).removeClass('page').addClass('page--on');
		$(thePageSwitcher).removeClass('page-switcher__page').addClass('page-switcher__page--on');

	},

	hide:function() {

		$('.page--on').removeClass('page--on').addClass('page');
		$('.page-switcher__page--on').removeClass('page-switcher__page--on').addClass('page-switcher__page');

	}

};

window[PROJECT_OBJECT].Pagination = {

	THRESHOLD 	: 14, // includes jump pages

	init:function() {

		console.log('.Pagination.init()');

		var self = this;

		$('.pagination').each(function() {

			var thisPagination 	= $(this),
				noPages			= $(this).find('li').length;

			// if there are more than 18 pages (14 = 10 pages with 4 jump pages)
			if (noPages >= self.THRESHOLD+8) {
				self.ellipsify(thisPagination);
			}

		});

		$(document).on('click','.pagination__more',function(e) {

			if ($(this).hasClass('pagination__more--open')) {
				self.contractPagination($(this));
			} else {
				self.expandPagination($(this));
			}

			e.preventDefault();
		});

	},

	ellipsify:function(p) {

		var self 			= this,
			thePagination 	= p,
			thePages 		= $(thePagination).find('li'),
			lastPage		= $(thePages).last(),
			selectedPage	= $(thePagination).find('.pagination__page--on');

			$(thePages).slice(self.THRESHOLD,$(thePages).length-1).addClass('pagination__page--hidden');
			$($(thePages).get(self.THRESHOLD)).before('<li class="pagination__more"><a href="#"><i class="material-icons">more_horiz</i></a></li>');
			$(thePagination).removeClass('pagination--indicated');

			if ($(thePages).index($(selectedPage)) > self.THRESHOLD-1) {
				$(thePagination).find('.pagination__more a').append('<span class="indicator">'+$(selectedPage).text()+'</span>');
				$(thePagination).addClass('pagination--indicated');
			}

	},

	expandPagination:function(m) {

		var self = this,
			theMoreControl 	= m,
			theIndicator 	= $(theMoreControl).find('.indicator'),
			thePages  		= $(theMoreControl).closest('.pagination').find('.pagination__page--hidden');

		$(theMoreControl).addClass('pagination__more--open');
		$(thePages).removeClass('pagination__page--hidden');
		$(theIndicator).hide();

	},

	contractPagination:function(m) {

		var self = this,
			theMoreControl 	= m,
			theIndicator 	= $(theMoreControl).find('.indicator'),
			thePages  		= $(theMoreControl).closest('.pagination').find('li').slice(self.THRESHOLD+1,$(theMoreControl).closest('.pagination').find('li').length-1);

		$(theMoreControl).removeClass('pagination__more--open');
		$(thePages).addClass('pagination__page--hidden');
		$(theIndicator).show();

	}

};
window[PROJECT_OBJECT].PillChoice = {

	init:function() {

		console.log('.PillChoice.init()');

		var self = this;

		self.setOnPageload();

		$(document).on('change','.pill-choice input,.pill-choice--small input', function() {
			self.setOn($(this));
		});

		$(document).on('click','.pill-choice__choice--on',function() {
			self.setRadioOff($('input[type="radio"]#'+$(this).attr('for')));
		});

	},

	setOn:function(i) {

		var theInput 		= i,
			theInputType	= $(theInput).attr('type'),
			theLabel 		= $('label[for="'+$(theInput).attr('id')+'"]'),
			otherRadios;

		if (theInputType == 'radio') {
			otherRadios = $('input[type="radio"][name="'+$(theInput).attr('name')+'"]');
			$(otherRadios).each(function() {
				$('label[for="'+$(this).attr('id')+'"]').removeClass('pill-choice__choice--on').addClass('pill-choice__choice');
			});
			$(theLabel).removeClass('pill-choice__choice').addClass('pill-choice__choice--on');
		}

		if (theInputType == 'checkbox') {
			if ($(theInput).is(':checked')) {
				$(theLabel).addClass('pill-choice__choice--on');
			} else {
				$(theLabel).removeClass('pill-choice__choice--on').addClass('pill-choice__choice');
			}
		}

	},

	setRadioOff:function(r) {

		var theRadio = r,
			theLabel = $('label[for="'+$(theRadio).attr('id')+'"]');

		$(theRadio).prop('checked',false);

		window[PROJECT_OBJECT].InputMores.hideArea(theRadio);

		setTimeout(
			function() {
				$(theLabel).removeClass('pill-choice__choice--on').addClass('pill-choice__choice');
			},
			150
		);

	},

	setOnPageload:function() {

		$('.pill-choice input, .pill-choice--small input').each(function() {

			var theInput = $(this);

			if ($(theInput).is(':checked')) {
				$('label[for="'+$(theInput).attr('id')+'"]').removeClass('pill-choice__choice').addClass('pill-choice__choice--on');
			}

		});

	}

};
window[PROJECT_OBJECT].ProgressBar = {

	update:function(p,a) {

		var theProgressBar 	= p,
			theDone 		= $(theProgressBar).find('.progress-bar__done'),
			theRemaining 	= $(theProgressBar).find('.progress-bar__remaining'),
			theAmount		= a;

		$(theDone).css('width',theAmount+'%');
		$(theRemaining).css('width',(100-parseFloat(theAmount))+'%');

	}

};
window[PROJECT_OBJECT].Quickpick = {

	init:function() {

		var self = this;

		$(document).on('change','.quickpick__choices input[type="radio"]',function(e) {
			self.setChoiceOn($(this));
		});

		$(document).on('click','.quickpick__clear',function() {
			var quickpick = $(this).closest('.quickpick');
			self.clearChoices(quickpick);
		});

	},

	setChoiceOn:function(c) {

		var theChoice 		= c,
			theLabel 		= $('label[for="'+$(theChoice).attr('id')+'"]'),
			allChoices		= $(theLabel).parent().parent(),
			theChosenOne	= $(allChoices).siblings('.quickpick__chosen'),
			theChoiceOn 	= $(allChoices).find('label.quickpick__choice--chosen');

		$(theChoiceOn).removeClass('quickpick__choice--chosen').addClass('quickpick__choice');
		$(theLabel).addClass('quickpick__choice--chosen');
		$(theChosenOne).text($(theLabel).text());

	},

	clearChoices:function(q) {

		var theQuickpick 	= q,
			theChoices		= $(theQuickpick).find('input[type="radio"]'),
			theChoiceOn 	= $(theQuickpick).find('label.quickpick__choice--chosen'),
			theChosenOne	= $(theQuickpick).find('.quickpick__chosen')

		$(theChoices).prop('checked',false);
		$(theChoiceOn).removeClass('quickpick__choice--chosen').addClass('quickpick__choice');
		$(theChosenOne).text('').addClass('.flash-bg').removeClass('.flash-bg');

	}

};
window[PROJECT_OBJECT].RepairPayment = {

	init:function() {

		console.log('.RepairPayment.init()');

	}

};
window[PROJECT_OBJECT].Repeater = {

	init:function() {

		console.log('.Repeater.init()');

		var self 	= this;

		$(document).on('click','.repeater__add',function(e) {

			var theRepeater = $(this).data('for') ? $('#'+$(this).data('for')) : $(this).closest('.repeater');
			self.addItem(theRepeater);
			e.preventDefault();

		});

		$(document).on('click','.repeater__remove',function(e) {

			var theItem = $(this).closest('.repeater__item');
			self.removeItem(theItem);
			e.preventDefault();

		});

	},

	addItem:function(r) {

		var self 			= this,
			theRepeater 	= r,
			theAdd 			= $(theRepeater).find('.repeater__add'),
			anItem			= $(theRepeater).find('.repeater__item').last(),
			newItem			= $(anItem).clone(),
			oldNewItemId 	= newItem.attr('id'),
			newItemId 		= '',
			newItemIndex	= 0,
			theCount 		= $(newItem).find('.repeater__count'),
			appendTo 		= $(theRepeater).find('.repeater__append').length ? $(theRepeater).find('.repeater__append') : $(theRepeater);

		if ($(theRepeater).hasClass('hidden')) {

			$(theRepeater).removeClass('hidden');

		} else {



			$(newItem).find('.repeater__remove').removeClass('hidden');

			$(newItem).appendTo($(appendTo));
			newItemIndex = $(theRepeater).find('.repeater__item').index(newItem);
			// $(theCount).text(newItemIndex+1);
			self.updateVisualCounters(theRepeater);

			// update the id of the repeater item
			newItemId = oldNewItemId.substring(0,oldNewItemId.indexOf('--rIndex-')+9)+(newItemIndex+1);
			$(newItem).attr('id',newItemId).show();

			// update any input ids and labels
			// $(newItem).find('input,textarea,select,.pill-choice--small,.pill-choice,.button--primary,.button--secondary').each(function()
			// {
			$(newItem).find('*[id*="--rIndex-"]').each(function() {

				var thisId 		= $(this).attr('id'),
					newId,
					thisName 	= $(this).attr('name'),
					newName,
					thisLabel 	= $('label[for="'+thisId+'"]').last(),
					newId 		= '',
					newIndex;

				if (thisId && thisId.indexOf('--rIndex') > -1) {

					newIndex = $(theRepeater).find('.repeater__item').index($(newItem))+1;
					newId = thisId.substring(0,thisId.indexOf('--rIndex-')+9)+newIndex;
					$(this).attr('id',newId);

				}

				if (thisName && thisName.indexOf('--rIndex') > -1) {

					newName = thisName.substring(0,thisName.indexOf('--rIndex-')+9)+newIndex;
					$(this).attr('name',newName);

				}

				if (thisLabel) {
					$(thisLabel).attr('for',newId);
				}

			});

		}

	},

	removeItem:function(i) {

		var self 			= this,
			theRepeaterItem = i,
			theRepeater 	= $(theRepeaterItem).closest('.repeater');

		theRepeaterItem.hide();

		self.updateVisualCounters(theRepeater);

	},

	// readjust the visual counters (code indexes - ids etc.. - stay the same)
	updateVisualCounters:function(r) {

		var theRepeater = r;

		$(theRepeater).find('.repeater__item:visible').each(function(i) {

			var theCount = $(this).find('.repeater__count');
			$(theCount).text(i+1);

		});

	}

};
window[PROJECT_OBJECT].Singlelist = {

	init:function() {

		var self = this;

		$('.singlelist').each(function() {

			var theSinglelist 	= $(this),
				theChecked	 	= $(theSinglelist).find('input[type="radio"]:checked');

			if (!$(theChecked).length) {
				theSinglelist.data('chosen-option',[]);
			} else {
				theSinglelist.data('chosen-options',$(theChecked).attr('id'));
				self.updateChosenItem(theSinglelist);
			}

			$(theSinglelist).data('default-text',$(theSinglelist).find($('.singlelist__chosen')).text());

		});

		$(document).on('change','.singlelist__options input[type="radio"], .singlelist__options--scrollable input[type="radio"]', function() {

			var theOptionId		= $(this).attr('id'),
				theOption 		= $('#'+theOptionId),
				theSinglelist 	= $(theOption).closest('.singlelist');

			theSinglelist.data('chosen-option',theOptionId);
			self.updateChosenItem(theSinglelist);

		});

		$(document).on('click','.singlelist__clear',function(e) {

			var theClearButton 	= $(this),
				theSinglelist	= $(theClearButton).closest('.singlelist'),
				theChosenArea	= $(theSinglelist).find('.singlelist__chosen,.singlelist__chosen--on'),
				theOptions		= $(theSinglelist).find('input[type="radio"]');

			$(theOptions).each(function() {
				$(this).prop('checked',false);
			});

			$(theChosenArea).text('Please select').removeClass('singlelist__chosen--on').addClass('singlelist__chosen');

			e.preventDefault();

		});

		$(document).on('keyup search','.singlelist__search',function() {
			self.searchList($(this).val(),$(this).siblings('.singlelist__options,.singlelist__options--scrollable'));
		});

	},

	updateChosenItem:function(s) {

		var theSinglelist 	= s,
			theChosenArea	= $(theSinglelist).find('.singlelist__chosen,.singlelist__chosen--on'),
			theChosen		= $(theSinglelist).find('input[type="radio"]:checked'),
			theText			= $('label[for="'+$(theChosen).attr('id')+'"]').text();

		$(theChosenArea).text(theText).removeClass('singlelist__chosen').addClass('singlelist__chosen--on');

		if(!$(theSinglelist).find('input[type="radio"]:checked').length) {
			$(theChosenArea).text($(theSinglelist).data('default-text')).removeClass('singlelist__chosen--on').addClass('singlelist__chosen');
		}
		window[PROJECT_OBJECT].Clickpanel.closeAll();

	},

	searchList:function(s,l) {

		var theSearchString = s.toLowerCase(),
			theList 		= l,
			theListItems	= $(theList).find('li'),
			matchPattern	= new RegExp(theSearchString);

		if (!theSearchString.length) {
			$(theListItems).removeClass('hidden');
			return;
		}

		$(theListItems).each(function() {

			var theListItem 	= $(this),
				theLabelText 	= $(theListItem).find('label').text().trim().toLowerCase();

			if (matchPattern.test(theLabelText)) {
				$(theListItem).removeClass('hidden');
			} else {
				$(theListItem).addClass('hidden');
			}

		});

	},

	selectItem:function(s,id) {

		var self 			= this,
			theSingleList 	= s,
			theOption		= $(theSingleList).find($('#'+id)),
			theOptions		= $(theOptions).find('input[type="radio"]');

		$(theOptions).each(function() {
			$(this).prop('checked',false);
		});

		$(theOption).prop('checked',true);

		self.updateChosenItem(theSingleList);

	},

	selectNone:function(s) {

		var self 			= this,
			theSingleList 	= s,
			theOptions		= theSingleList.find('input[type="radio"]');

		$(theOptions).each(function() {
			$(this).prop('checked',false);
		});

		self.updateChosenItem(theSingleList);

	}

};
window[PROJECT_OBJECT].Slip = {

	init:function() {

		console.log('.Slip.init()');

		var self 			= this,
			slip 			= $('#slip');

		$(document).on('click', '.slip-open-trigger', function(e) {
			self.open();
			e.preventDefault();
		});

		$(document).on('click', '.slip-close-trigger', function(e) {
			self.close();
			e.preventDefault();
		});

		$(slip).resizable({

		    grid: [30, 30],
		    handles:"n",
		    ghost:true,
		    maxHeight:400,
		    minHeight:150,

		    stop:function() {
		    	self.moveBlotterSummary();
		    }

		});

	},

	adjust:function() {

		if (window[PROJECT_OBJECT].Navigation.isOpen()) {
			$('.slip__content--large').removeClass('slip__content--large').addClass('slip__content--small');
		} else {
			$('.slip__content--small').removeClass('slip__content--small').addClass('slip__content--large');
		}

	},

	open:function() {
		var self = this;
		self.moveBlotterSummary();
		$('#slip').removeClass('slip--closed').addClass('slip--open');
	},

	close:function() {
		var blotterSummary 	= $('#blotter-summary');
		$(blotterSummary).css('bottom','10px');
		$('#slip').removeClass('slip--open').addClass('slip--closed');
	},

	moveBlotterSummary:function() {

		var blotterSummary 	= $('#blotter-summary'),
			slip 			= $('#slip'),
			slipHeight,
			slipHeightVal;

		slipHeight		= $(slip).css('height');
		slipHeightVal	= parseInt(slipHeight.substring(0,slipHeight.length-2));

    	if ($(blotterSummary).length) {
    		$(blotterSummary).css('bottom',(slipHeightVal+10)+'px');
    	}

	}

};
window[PROJECT_OBJECT].Tabs = {

	init:function() {

		console.log('.Tabs.init()');

		$(document).on('click','.tabs__tab',function(e) {

			var tab 		= $(this),
				tabIndex	= $(tab).parent().find('li').index($(tab));

			$(tab).siblings('.tabs__tab--on').removeClass('tabs__tab--on').addClass('tabs__tab');
			$(tab).removeClass('tabs__tab').addClass('tabs__tab--on');

			$(tab).parent().siblings().removeClass('tabs__content--on').addClass('tabs__content');
			$(tab).parent().siblings().eq(tabIndex).removeClass('tabs__content').addClass('tabs__content--on');

			e.preventDefault();

		});

	}

};
window[PROJECT_OBJECT].Tables = {

	init:function() {

		console.log('.NewTables.init()');

		var self = this;

		if ($('.table-fix').length) {
			self.initFixedTableHeaders();
		}

		if ($('.scrolltable').length) {
			self.initScrolltable();
		}

		$(document).on('click','.table-expand__trigger',function(e) {

			var theRow = $('#'+$(this).data('expand-row-id'));

			if ($(theRow).hasClass('table-expand__more--closed')) {
				self.openRow(theRow);
			} else {
				self.closeRow(theRow);
			}

			e.preventDefault();

		});

		/*
		 * $(document).on('click','.table-expandable--trigger',function(e) {
		 * 
		 * var parentTR = $(this).parent().parent(), btnMore = $(this);
		 * 
		 * if ($(parentTR).hasClass('table-expandable--open')) {
		 * self.contractRow($(parentTR),$(btnMore)); } else {
		 * self.expandRow($(parentTR),$(btnMore)); }
		 * 
		 * e.preventDefault();
		 * 
		 * });
		 * 
		 * $(document).on('click','.table-expandable .quick-controls
		 * .button--more',function(e) {
		 * 
		 * var icon = $(this).find('.material-icons').get(0);
		 * 
		 * if ($(icon).hasClass('material-icons--open')) {
		 * self.expandAllRows($(this)); } else { self.contractAllRows($(this)); }
		 * e.preventDefault();
		 * 
		 * });
		 */

		if ($('.table-rowsdrag').length) {
			self.initDraggableRows();
		}

		if ($('.colpicker').length) {

			$('.colpicker').each(function() {
				self.initColpicker($(this));
			});

		}

	},

	initFixedTableHeaders:function() {

		var self = this;

		var syncScrollableHeaders = function() {

			// when the scrollable table scrolls, then scroll their fixed
			// headers too
			$('.scrolltable').scroll(function() {
				$('#scrolltable-headers').prop('scrollLeft',this.scrollLeft);
			});

			// when the scrollable table headers scroll, then scroll the table
			// too
			$('#scrolltable-headers').scroll(function() {
				$('.scrolltable').prop('scrollLeft',this.scrollLeft);
			});

		};

		var createFixedHeaders = function() {

			var theTable 		= $('.table-fix'),
				extraClasses 	= '',
				headerHTML		= '';

			// if the table is a micro table, then the headers should be too
			if ($(theTable).hasClass('micro')) {
				extraClasses += 'micro';
			}

			// build the html for the headers
			if ($(theTable).hasClass('scrolltable__table')) {
				headerHTML = '<div id="scrolltable-headers" class="table-headers--hidden"><table id="table-headers-scrollable" class="'+extraClasses+'"><thead>'+theTable.find('thead tr').first().html()+'</thead></table></div>';
				syncScrollableHeaders();
			} else {
				headerHTML = '<table id="table-headers" class="'+extraClasses+' table-headers--hidden"><thead>'+theTable.find('thead tr').first().html()+'</thead></table>';
			}

			// add the headers before the table
			theTable.before($(headerHTML));

			self.sizeFixedHeaders();

		};

		var monitorScrollForHeaders = function() {

			var rTime,
				timeout 	= false,
				delta 		= 100;

			$(window).on('scroll',function() {

				rTime = new Date();
				if (timeout === false) {
					timeout === true;
					setTimeout(doFix,delta);
				}

			});

			var doFix = function() {

				if (new Date() - rTime < delta) {
		        	setTimeout(doFix, delta);
			    } else {

			        timeout = false;

			        if(!$('.table-fix thead').isOnScreen() && $(window).scrollTop() > 200) {

			        	if ($('#table-headers').length) {
			        		self.showFixedHeaders();
			        		self.leftifyFixedHeaders();
			        	}

			        	if ($('#scrolltable-headers').length) {
			        		if ($(window).scrollLeft() == 0 && $('.scrolltable').isOnScreen(0,0)) {
			        			self.showFixedHeaders();
			        			// self.leftifyFixedHeaders();
			        			syncScrollableHeaders();
			        		} else {
			        			self.hideFixedHeaders();
			        		}
			        	}

			        } else {
			        	self.hideFixedHeaders();
			        }

			        /*
					 * if ($(window).scrollLeft() > 0 &&
					 * $('.scrolltable').length) { self.hideFixedHeaders(); }
					 */

			    }

			}

		}

		// size headers when user resizes window
		var sortHeadersOnResize = debounce(function() {
			self.sizeFixedHeaders();
		}, 200);
		window.addEventListener('resize', sortHeadersOnResize);

		createFixedHeaders();
		self.hideFixedHeaders();
		self.sizeFixedHeaders();
		if ($('#table-headers,#scrolltable-headers').length && !$('.table-fix thead').isOnScreen(0,0)) {
			self.originalScrollHeadersLeft = $('#scrolltable-headers').css('left');
    		self.showFixedHeaders();
    	}
		monitorScrollForHeaders();

	},

	showFixedHeaders:function() {
		var self = this;
		$('#table-headers,#scrolltable-headers').removeClass('table-headers--hidden').addClass('table-headers--visible');
		// self.leftifyFixedHeaders();
	},

	hideFixedHeaders:function() {
		$('#table-headers,#scrolltable-headers').removeClass('table-headers--visible').addClass('table-headers--hidden');
	},

	sizeFixedHeaders:function() {

		$('#table-headers,#table-headers-scrollable').css('width',$('.table-fix').width()+'px');

		setTimeout(function() {
			$('#scrolltable-headers').css('width',$('.scrolltable').innerWidth()+'px');
			$('#scrolltable-headers table').css('width',$('.scrolltable__table').width()+'px');
		}, 200);

		$('#table-headers th, #table-headers-scrollable th').each(function(i) {

			var thisTH 		= $(this),
				originalTH	= $('.table-fix thead tr th').get(i),
				thisLeft 	= $(thisTH).css('left'),
				thisLeftVal = thisLeft ? parseInt(thisLeft.substring(0,thisLeft.length-2)) : 0;

			$(thisTH).css({'width':$(originalTH).innerWidth()+'px'});

		});

	},

	leftifyFixedHeaders:function() {

		var self 						= this,
			currentHPos 				= $(document).scrollLeft(),
			fixedHeadersLeftPos 		= 0,
			currentScrollHeadersLeft 	= 0,
			currentScrollHeadersLeftVal	= 0,
			newScrollHeadersLeftVal,
			noFixedHeaders 				= $('.scrolltable__table th.scrolltable__fixed').length,
			sHeadersLeft,
			sHeadersLeftVal,
			newSHeadersLeftVal;

		if ($('#table-headers').length) {

			fixedHeadersLeftPos = 124 - currentHPos;

			/*
			 * if (window[PROJECT_OBJECT].Navigation.isOpen()) {
			 * fixedHeadersLeftPos = 340 - currentHPos; } else {
			 * fixedHeadersLeftPos = 124 - currentHPos; }
			 */

			$('#table-headers').css('left',fixedHeadersLeftPos+'px');

		} else { // if there are scrollable headers

			sHeadersLeft = $('#scrolltable-headers').css('left');
			sHeadersLeftVal	= parseInt(sHeadersLeft.substring(0,sHeadersLeft.length-2));

			if (window[PROJECT_OBJECT].Navigation.isOpen()) {

				// 1. move the fixed column headers
				$('#table-headers-scrollable .scrolltable__fixed').each(function(i) {
					var sfLeft 		= $(this).css('left'),
						sfLeftVal	= parseInt(sfLeft.substring(0,sfLeft.length-2));
						newSfLeft	= sfLeftVal + 215;
					$(this).css('left',newSfLeft+'px');
				});

				// 2. move the scrollable headers
				newSHeadersLeftVal = sHeadersLeftVal + 215;

			} else if (window[PROJECT_OBJECT].Navigation.hasBeenOpenedOnce()) { // closed
																				// but
																				// has
																				// been
																				// opened
																				// once

				// 1. move the fixed column headers
				$('#table-headers-scrollable .scrolltable__fixed').each(function(i) {
					var sfLeft 		= $(this).css('left'),
						sfLeftVal	= parseInt(sfLeft.substring(0,sfLeft.length-2));
						newSfLeft	= sfLeftVal - 215;
					$(this).css('left',newSfLeft+'px');
				});

				// 2. move the scrollable headers
				newSHeadersLeftVal = sHeadersLeftVal - 215;

			} else {
				newSHeadersLeftVal = sHeadersLeftVal;
			}

			// 2. move the scrollable headers
			$('#scrolltable-headers').css('left',newSHeadersLeftVal+'px');








			// move the scrollable area
			// currentScrollHeadersLeft = $('#scrolltable-headers').css('left');
			// currentScrollHeadersLeftVal =
			// parseInt(currentScrollHeadersLeft.substring(0,currentScrollHeadersLeft.length-2));

			// if (window[PROJECT_OBJECT].Navigation.isOpen()) {
			// newScrollHeadersLeftVal = currentScrollHeadersLeftVal + 216;
			// } else {
			// newScrollHeadersLeftVal =
			// parseInt(self.originalScrollHeadersLeft.substring(0,self.originalScrollHeadersLeft.length-2));

			// }

			// // move the scrollable headers
			// $('#scrolltable-headers').css('left',newScrollHeadersLeftVal+'px');

			// move the fixed headers





		}

	},

	originalScrollHeadersLeft : 0,

	initScrolltable:function() {

		var self 				= this,
			theTableContainer	= $('.scrolltable'),
			theMarginLeft		= $(theTableContainer).css('margin-left'),
			theMarginLeftVal	= parseInt(theMarginLeft.substring(0,theMarginLeft.length-2)),
			tableWidth;

		self.originalScrollHeadersLeft = $('#scrolltable-headers').css('left');

		if ($('.grid-annex-main').length) {
			tableWidth = $('.grid-annex-main').innerWidth()-theMarginLeftVal;
		} else {
			tableWidth = $('#main-content__body').innerWidth()-(theMarginLeftVal+60);
		}

		$(theTableContainer).css('width',tableWidth+'px');

		if (!$('.grid-annex-main').length) {
			$('#main-content__body').css('position','relative');
		}

	},

	openRow:function(tr) {

		var theRow = tr;

		$(theRow).removeClass('table-expand__more--closed').addClass('table-expand__more--open');

		/*
		 * var row = tr, btn = btn, icon = btn.find('.material-icons').get(0);
		 * 
		 * $(row).addClass('table-expandable--open');
		 * $(row).next('.table-expandable__more').addClass('table-expandable__more--open');
		 * window[PROJECT_OBJECT].Util.toggleMoreIcon(icon,true);
		 */

	},

	closeRow:function(tr) {

		var theRow = tr;

		$(theRow).removeClass('table-expand__more--open').addClass('table-expand__more--closed');

		/*
		 * var row = tr, btn = btn, icon = btn.find('.material-icons').get(0);
		 * 
		 * $(row).removeClass('table-expandable--open');
		 * $(row).next('.table-expandable__more').removeClass('table-expandable__more--open');
		 * window[PROJECT_OBJECT].Util.toggleMoreIcon(icon,false);
		 */

	},

	expandAllRows:function(btn) {

		var self 			= this,
			btn 			= btn,
			theTableRows	= $(btn).closest('.table-expandable').find('tbody tr:not(.table-expandable__more)');

		self.expandRow(theTableRows,btn);
		window[PROJECT_OBJECT].Util.toggleMoreIcon($(theTableRows).find('.button--more .material-icons'),true);

	},

	contractAllRows:function(btn) {

		var self 			= this,
			btn 			= btn,
			theTableRows	= $(btn).closest('.table-expandable').find('tbody tr:not(.table-expandable__more)');

		self.contractRow(theTableRows,btn);
		window[PROJECT_OBJECT].Util.toggleMoreIcon($(theTableRows).find('.button--more .material-icons'),false);

	},

	initColpicker:function(cp) {

		var theColPicker 		= $(cp),
			theColPickerOptions	= $(theColPicker).find('.multilist__options'),
			theTable 	 		= $('#'+$(theColPicker).data('table'));

		function updateInfo() {

			var totalCols = $(theTable).find('th').length,
				totalColsHidden = $(theTable).find('th.hidden').length,
				infoElem = $('.colpicker-hidden[data-table="'+$(theTable).attr('id')+'"]');

			$(infoElem).find('.colpicker-hidden__amount').text(totalColsHidden);

		}

		updateInfo();

		// first build the options
		$(theTable).find('thead th:not(.drag-cell)').each(function(i) {

			var chkId = 'chk-colpicker-'+$(theTable).attr('id')+'-'+i,
				chked = $(this).hasClass('hidden') ? '':'checked';

			$(theColPickerOptions).append('<li><label for="'+chkId+'"><input id="'+chkId+'" class="chk-colpicker" type="checkbox" '+chked+'/>'+$(this).text()+'</label></li>')

		});

		// then make it work
		$(document).on('click','.chk-colpicker, .colpicker .multilist__pill', function() {

			function hideCol(i) {

				if ($(theTable).find('.drag-cell').length) {
					i = i+1;
				}

				$(theTable).find('th').eq(i).addClass('hidden');

				$(theTable).find('tr').each(function() {
					$(this).find('td').eq(i).addClass('hidden');
				});

				updateInfo();

			}

			function showCol(i) {

				if ($(theTable).find('.drag-cell').length) {
					i = i+1;
				}

				$(theTable).find('th').eq(i).removeClass('hidden');

				$(theTable).find('tr').each(function() {
					$(this).find('td').eq(i).removeClass('hidden');
				});

				updateInfo();

			}

			var chkBx 		= $(this).hasClass('multilist__pill') ? $('#'+$(this).data('id-to-remove')) : $(this),
				allChks 	= $(chkBx).closest('ul').find('.chk-colpicker'),
				colIndex	= $(allChks).index($(chkBx));

			if ($(this).hasClass('chk-colpicker')) {

				if ($(this).is(':checked')) {
					showCol(colIndex);
				} else {
					hideCol(colIndex);
				}

			} else {
				hideCol(colIndex);
			}

		});

	},

	initDraggableRows:function() {

		// Helper function to keep table row from collapsing when being sorted

		var fixHelperModified = function(e, tr) {

			var $originals = tr.children();
			var $helper = tr.clone();

			$helper.children().each(function(index) {
				$(this).width($originals.eq(index).width())
			});

			return $helper;
		};

		// Make diagnosis table sortable

		$(".table-rowsdrag tbody").sortable({
	    	helper: fixHelperModified,
	    	placeholder: "drop-highlight"
		});

	},

	removeTableFiltersList:function() {
		$('.section--results').remove();
	}

};




































/*
 * window[PROJECT_OBJECT].Tables = {
 * 
 * init:function() {
 * 
 * console.log('.Tables.init()');
 * 
 * var self = this;
 * 
 * $(document).on('click','.table-expandable .button--more',function(e) {
 * 
 * var parentTR = $(this).parent().parent(), btnMore = $(this);
 * 
 * if ($(parentTR).hasClass('table-expandable--open')) {
 * self.contractRow($(parentTR),$(btnMore)); } else {
 * self.expandRow($(parentTR),$(btnMore)); }
 * 
 * e.preventDefault();
 * 
 * });
 * 
 * $(document).on('click','.table-expandable .quick-controls
 * .button--more',function(e) {
 * 
 * var icon = $(this).find('.material-icons').get(0);
 * 
 * if ($(icon).hasClass('material-icons--open')) { self.expandAllRows($(this)); }
 * else { self.contractAllRows($(this)); } e.preventDefault();
 * 
 * });
 * 
 * if ($('.table-rowsdrag').length) { self.initDraggableRows(); }
 * 
 * if ($('.colpicker').length) {
 * 
 * $('.colpicker').each(function() { self.initColpicker($(this)); });
 *  }
 * 
 * if ($('.table-fix').length) {
 * 
 * self.createFixedTableHeaders(); self.monitorScrollForHeaders();
 * 
 * if(!$('.table-fix thead').isOnScreen() && $(window).scrollTop()>200) {
 * self.showFixedHeaders(); } else { self.hideFixedHeaders(); }
 * 
 * var sortHeadersOnResize = debounce(function() { self.sizeFixedHeaders(); },
 * 200);
 * 
 * window.addEventListener('resize', sortHeadersOnResize); }
 * 
 * if ($('.filter-row').length) {
 * 
 * self.monitorFilters(); self.rightifyFilterActions();
 * 
 * var sortFilterActionsOnResize = debounce(function() {
 * self.rightifyFilterActions(); }, 200);
 * 
 * window.addEventListener('resize', sortFilterActionsOnResize); }
 * 
 * 
 * 
 * if ($('.scrolltable').length) {
 * 
 * $('.scrolltable').each(function() { self.initScrolltable($(this)); });
 *  }
 *  },
 * 
 * expandRow:function(tr,btn) {
 * 
 * var row = tr, btn = btn, icon = btn.find('.material-icons').get(0);
 * 
 * $(row).addClass('table-expandable--open');
 * $(row).next('.table-expandable__more').addClass('table-expandable__more--open');
 * window[PROJECT_OBJECT].Util.toggleMoreIcon(icon,true);
 *  },
 * 
 * contractRow:function(tr,btn) {
 * 
 * var row = tr, btn = btn, icon = btn.find('.material-icons').get(0);
 * 
 * $(row).removeClass('table-expandable--open');
 * $(row).next('.table-expandable__more').removeClass('table-expandable__more--open');
 * window[PROJECT_OBJECT].Util.toggleMoreIcon(icon,false);
 *  },
 * 
 * expandAllRows:function(btn) {
 * 
 * var self = this, btn = btn, theTableRows =
 * $(btn).closest('.table-expandable').find('tbody
 * tr:not(.table-expandable__more)');
 * 
 * self.expandRow(theTableRows,btn);
 * window[PROJECT_OBJECT].Util.toggleMoreIcon($(theTableRows).find('.button--more
 * .material-icons'),true);
 *  },
 * 
 * contractAllRows:function(btn) {
 * 
 * var self = this, btn = btn, theTableRows =
 * $(btn).closest('.table-expandable').find('tbody
 * tr:not(.table-expandable__more)');
 * 
 * self.contractRow(theTableRows,btn);
 * window[PROJECT_OBJECT].Util.toggleMoreIcon($(theTableRows).find('.button--more
 * .material-icons'),false);
 *  },
 * 
 * initColpicker:function(cp) {
 * 
 * var theColPicker = $(cp), theColPickerOptions =
 * $(theColPicker).find('.multilist__options'), theTable =
 * $('#'+$(theColPicker).data('table'));
 * 
 * function updateInfo() {
 * 
 * var totalCols = $(theTable).find('th').length, totalColsHidden =
 * $(theTable).find('th.hidden').length, infoElem =
 * $('.colpicker-hidden[data-table="'+$(theTable).attr('id')+'"]');
 * 
 * $(infoElem).find('.colpicker-hidden__amount').text(totalColsHidden);
 *  }
 * 
 * updateInfo();
 *  // first build the options $(theTable).find('thead
 * th:not(.drag-cell)').each(function(i) {
 * 
 * var chkId = 'chk-colpicker-'+$(theTable).attr('id')+'-'+i, chked =
 * $(this).hasClass('hidden') ? '':'checked';
 * 
 * $(theColPickerOptions).append('<li><label for="'+chkId+'"><input
 * id="'+chkId+'" class="chk-colpicker" type="checkbox"
 * '+chked+'/>'+$(this).text()+'</label></li>')
 * 
 * });
 *  // then make it work $(document).on('click','.chk-colpicker, .colpicker
 * .multilist__pill', function() {
 * 
 * function hideCol(i) {
 * 
 * if ($(theTable).find('.drag-cell').length) { i = i+1; }
 * 
 * $(theTable).find('th').eq(i).addClass('hidden');
 * 
 * $(theTable).find('tr').each(function() {
 * $(this).find('td').eq(i).addClass('hidden'); });
 * 
 * updateInfo();
 *  }
 * 
 * function showCol(i) {
 * 
 * if ($(theTable).find('.drag-cell').length) { i = i+1; }
 * 
 * $(theTable).find('th').eq(i).removeClass('hidden');
 * 
 * $(theTable).find('tr').each(function() {
 * $(this).find('td').eq(i).removeClass('hidden'); });
 * 
 * updateInfo();
 *  }
 * 
 * var chkBx = $(this).hasClass('multilist__pill') ?
 * $('#'+$(this).data('id-to-remove')) : $(this), allChks =
 * $(chkBx).closest('ul').find('.chk-colpicker'), colIndex =
 * $(allChks).index($(chkBx));
 * 
 * if ($(this).hasClass('chk-colpicker')) {
 * 
 * if ($(this).is(':checked')) { showCol(colIndex); } else { hideCol(colIndex); }
 *  } else { hideCol(colIndex); }
 * 
 * });
 *  },
 * 
 * initDraggableRows:function() {
 *  // Helper function to keep table row from collapsing when being sorted
 * 
 * var fixHelperModified = function(e, tr) {
 * 
 * var $originals = tr.children(); var $helper = tr.clone();
 * 
 * $helper.children().each(function(index) {
 * $(this).width($originals.eq(index).width()) });
 * 
 * return $helper; };
 *  // Make diagnosis table sortable
 * 
 * $(".table-rowsdrag tbody").sortable({ helper: fixHelperModified, placeholder:
 * "drop-highlight" });
 *  },
 * 
 * createFixedTableHeaders:function() {
 * 
 * var self = this;
 * 
 * $('.table-fix').each(function() {
 * 
 * var theTable = $(this), extraClasses = "";
 * 
 * if ($(theTable).hasClass('micro')) { extraClasses += 'micro'; }
 * 
 * if ($(theTable).hasClass('scrolltable__table')) {
 * 
 * theTable.before('<div id="scrolltable-headers"
 * class="table-headers--hidden"><table id="table-headers-scrollable"
 * class="'+extraClasses+'"><thead>'+theTable.find('thead tr').first().html()+'</thead></table></div>');
 * self.syncScrollableHeaders();
 *  } else {
 * 
 * theTable.before($('<table id="table-headers" class="'+extraClasses+'
 * table-headers--hidden"><thead>'+theTable.find('thead tr').first().html()+'</thead></table>'));
 *  } });
 * 
 * self.sizeFixedHeaders();
 *  },
 * 
 * sizeFixedHeaders:function() {
 * 
 * var extraLeft = 0;
 * 
 * if ($('.grid-annex-main').length && $('#table-headers-scrollable').length) {
 * extraLeft = 125; }
 * 
 * $('#table-headers').css('width',$('.table-fix').width()+'px');
 * 
 * setTimeout(function() {
 * $('#scrolltable-headers').css('width',$('.scrolltable').width()+'px');
 * $('#scrolltable-headers
 * table').css('width',$('.scrolltable__table').width()+'px'); }, 200);
 * 
 * 
 * $('#table-headers th, #table-headers-scrollable th').each(function(i) {
 * 
 * var thisTH = $(this), originalTH = $('.table-fix thead tr th').get(i),
 * thisLeft = $(thisTH).css('left'), thisLeftVal = thisLeft ?
 * parseInt(thisLeft.substring(0,thisLeft.length-2)) : 0;
 * 
 * $(thisTH).css({'width':$(originalTH).innerWidth()+'px','left':(thisLeftVal+extraLeft)+'px'});
 * 
 * });
 *  },
 * 
 * leftifyFixedHeaders:function() {
 * 
 * var currentHPos = $(document).scrollLeft(), fixedHeadersLeftPos = 124 -
 * currentHPos;
 * 
 * if ($('.main-nav--maximised').length && !$('#scrolltable-headers').length) {
 * fixedHeadersLeftPos = 340 - currentHPos;
 * $('#table-headers').css('left',fixedHeadersLeftPos+'px'); }
 *  },
 * 
 * leftifyScrollableHeaders:function() {
 * 
 * var self = this, currentHPos = $(document).scrollLeft(), fixedHeadersLeftPos =
 * 0, scrollableTableHeadersLeft = $('#scrolltable-headers').css('left'),
 * scrollableTableHeadersLeftVal = 0;
 * 
 * if ($('#scrolltable-headers').length) {
 * 
 * scrollableTableHeadersLeftVal =
 * parseInt(scrollableTableHeadersLeft.substring(0,scrollableTableHeadersLeft.length-2));
 * 
 * fixedHeadersLeftPos = scrollableTableHeadersLeftVal;
 * 
 * if (window[PROJECT_OBJECT].Navigation.isOpen()) {
 * 
 * if (currentHPos == 0) { fixedHeadersLeftPos += 215; }
 *  } else { fixedHeadersLeftPos -= 215; }
 * 
 * $('#scrolltable-headers').css('left',fixedHeadersLeftPos+'px');
 *  }
 *  },
 * 
 * showFixedHeaders:function() {
 * $('#table-headers,#scrolltable-headers').removeClass('table-headers--hidden').addClass('table-headers--visible'); },
 * 
 * hideFixedHeaders:function() {
 * $('#table-headers,#scrolltable-headers').removeClass('table-headers--visible').addClass('table-headers--hidden'); },
 * 
 * syncScrollableHeaders:function() {
 * 
 * $('.scrolltable').scroll(function() {
 * $('#scrolltable-headers').prop('scrollLeft',this.scrollLeft); });
 * 
 * $('#scrolltable-headers').scroll(function() {
 * $('.scrolltable').prop('scrollLeft',this.scrollLeft); });
 *  },
 * 
 * monitorScrollForHeaders:function() {
 * 
 * var rTime, timeout = false, delta = 100, lastHPos = 0, self = this;
 * 
 * $(window).on('scroll',function() {
 * 
 * rTime = new Date(); if (timeout === false) { timeout === true;
 * setTimeout(doFix,delta); }
 * 
 * });
 * 
 * var doFix = function() {
 * 
 * if (new Date() - rTime < delta) { setTimeout(doFix, delta); } else { timeout =
 * false; self.rightifyFilterActions();
 * 
 * if(!$('.table-fix thead').isOnScreen() && $(window).scrollTop()>200) {
 * 
 * if ($('#table-headers').length) { self.showFixedHeaders();
 * self.leftifyFixedHeaders(); }
 * 
 * if ($('#scrolltable-headers').length) { if ($(window).scrollLeft() > 0 ||
 * !$('.scrolltable').isOnScreen(0,0)) { self.hideFixedHeaders(); } else {
 * self.showFixedHeaders(); } }
 *  } else { self.hideFixedHeaders(); }
 * 
 * if (!$('.table-fix thead').isOnScreen() && $('#scrolltable-headers').length &&
 * $(window).scrollLeft() > 0) { self.leftifyScrollableHeaders(); }
 *  }
 *  }
 *  },
 * 
 * 
 * rightifyFilterActions:function() {
 * 
 * var currentHPos = $(document).scrollLeft(), filterRowActionsLeftPos =
 * ($(window).width()+currentHPos)-168;
 * 
 * if ($('.main-nav--maximised').length) { filterRowActionsLeftPos =
 * ($(window).width()+currentHPos)-384; }
 * 
 * $('.filter-row__actions--visible').css('left',filterRowActionsLeftPos+'px');
 *  },
 * 
 * showFilterRowActions:function() {
 * $('.filter-row__actions--hidden').removeClass('filter-row__actions--hidden').addClass('filter-row__actions--visible'); },
 * 
 * hideFilterRowActions:function() {
 * $('.filter-row__actions--visible').removeClass('filter-row__actions--visible').addClass('filter-row__actions--hidden'); },
 * 
 * 
 * 
 * initScrolltable:function(t) {
 * 
 * var theTable = t, theMarginLeft = $(theTable).css('margin-left'),
 * theMarginLeftVal =
 * parseInt(theMarginLeft.substring(0,theMarginLeft.length-2)), tableWidth;
 * 
 * if ($('.grid-annex-main').length) { tableWidth =
 * $('.grid-annex-main').innerWidth()-theMarginLeftVal; } else { tableWidth =
 * $('#main-content__body').innerWidth()-(theMarginLeftVal+60); }
 * 
 * $(theTable).css('width',tableWidth+'px');
 *  }
 *  };
 * 
 */



window[PROJECT_OBJECT].Toggle = {

	init:function() {

		console.log('.Toggle.init()');

		var self = this;

		$(document).on('click','.toggle__option,.toggle__option--on', function(e) {

 			var theToggle = $(this).parent();
			self.toggle(theToggle,$(this));
			e.preventDefault();

		});

	},

	toggle:function($t, $tOn) {

		var theToggle 		= $t,
			theToggleOn		= $tOn;

		$(theToggle).find('.toggle__option--on').removeClass('toggle__option--on').addClass('toggle__option');
		$(theToggleOn).removeClass('toggle__option').addClass('toggle__option--on');

	}

};
window[PROJECT_OBJECT].Tooltips = {

	init:function() {

		$('[data-ot-show-on-load]').each(function() {

			var loadTip	= new Opentip($(this),$(this).data('ot-show-on-load'), {target:true,hideOn:'mousemove'});

			loadTip.show();

		});

	},

	hideAll:function() {
		for(var i = 0; i < Opentip.tips.length; i ++) {
			Opentip.tips[i].hide();
		}
	},

	deactivate:function(e) {

		var theElement = e;

		for (var i = 0; i < Opentip.tips.length; i ++) {

	 		var tip = Opentip.tips[i];

	  		if ($(tip.triggerElement).get(0) === $(theElement).get(0)) {
	  			tip.deactivate();
	  		}

		}

	},

	activate:function(e) {

		var theElement = e;

		for (var i = 0; i < Opentip.tips.length; i ++) {

	 		var tip = Opentip.tips[i];

	  		if ($(tip.triggerElement).get(0) === $(theElement).get(0)) {
	  			tip.activate();
	  		}

		}

	}

};
/* Main */

window[PROJECT_OBJECT].Main = {

	init:function() {

		console.log('.Main.init()');

		window[PROJECT_OBJECT].Util.bindEscapeKey();
		window[PROJECT_OBJECT].Util.initSmoothScrolls();
		window[PROJECT_OBJECT].Navigation.init();
		window[PROJECT_OBJECT].Drawer.init();
		window[PROJECT_OBJECT].Tables.init();

		window[PROJECT_OBJECT].AcceptLearn.init();
		window[PROJECT_OBJECT].Accordions.init();
		window[PROJECT_OBJECT].Annex.init();
		window[PROJECT_OBJECT].Clickpanel.init();
		window[PROJECT_OBJECT].Expand.init();
		window[PROJECT_OBJECT].Filters.init();
		window[PROJECT_OBJECT].FilterLists.init();
		window[PROJECT_OBJECT].Forms.init();
		window[PROJECT_OBJECT].Hoverpanels.init();
		window[PROJECT_OBJECT].InputMores.init();
		window[PROJECT_OBJECT].Modal.init();
		window[PROJECT_OBJECT].Multilist.init();
		window[PROJECT_OBJECT].PageSwitcher.init();
		window[PROJECT_OBJECT].Pagination.init();
		window[PROJECT_OBJECT].PillChoice.init();
		window[PROJECT_OBJECT].Quickpick.init();
		window[PROJECT_OBJECT].RepairPayment.init();
		window[PROJECT_OBJECT].Repeater.init();
		window[PROJECT_OBJECT].Singlelist.init();
		window[PROJECT_OBJECT].Slip.init();
		window[PROJECT_OBJECT].Tabs.init();

		window[PROJECT_OBJECT].Toggle.init();
		window[PROJECT_OBJECT].Tooltips.init();

		window[PROJECT_OBJECT].Util.removePageloads();

		window[PROJECT_OBJECT].Util.initDevNotes();

	}

};
window[PROJECT_OBJECT].Main.init();
}); // ends document.ready from header.js
// # sourceMappingURL=_maps/cd.js.map

function showBlackListNames(dataType) {
	setAdministrationPermission();
	$("#modal-private-name-blacklist").css('display', 'block');
	$("#popupHeading").text(dataType);
	$("#addBlackListType").val(dataType);
	$("#searchBlackListType").val(dataType);
	changeLabelDynamically(dataType);
	
	var blacklistDataType = dataType;
	var request = {};
	addField('blacklistDataType', blacklistDataType, request);
	$.ajax({
		url : '/internalrule-service/showBlacklistData',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			showBlackListDataResponse(data.blacklistDataResponse);
		},
		error : function() {
			alert('Error while fetching openBlackListName data');
		}
	});
}

function showBlackListDataResponse(data) {
	var jsonString = data;
	var dataType = $("#addBlackListType").val();
	var table;
	var user = getJsonObject(getValueById('userInfo'));
	var permissions = user.permissions;
	
	if(null != jsonString && jsonString != ''){
		for(var i=0; i<jsonString.length; i++){
			if (permissions.canNotLockAccount){
				table += '<tr><td class="small-cell"><a href="#" onclick = "deleteBlackListData(\''+dataType+'\',\''+jsonString[i].createdOnDate+'\' ,\''+jsonString[i].value+' \')" </a></td>';
			}else{
				table += '<tr><td class="small-cell"><a href="#" onclick = "deleteBlackListData(\''+dataType+'\',\''+jsonString[i].createdOnDate+'\' ,\''+jsonString[i].value+' \')" class="material-icons">close</a></td>';
			}
			table += '<td style="width: 200px;">' + jsonString[i].createdOnDate + '</td>';
			table += '<td>' + jsonString[i].value + '</td>';
			table += '<td class = "breakword" style="width: 200px;">' + getEmptyIfNull(jsonString[i].notes) + '</td></tr>';
		}	
	}else{
		table += '<tr><td></td>';
		table += '<td style="width: 200px;"> No data found </td></tr>';
	}
	$("#emptyErrorDiv").css('display', 'none');
	$('#blackListNameTableBody').append(table);
}

var timeOutObject = 0;

function closePopUp(){
	if(timeOutObject) {
		clearTimeout(timeOutObject);
	}
	$('#repeatCheckError, #dateErrorDiv, #repeatCheckComplete, #dateComparisonErrorDiv, #repeatForceCheckComplete, #dateComparisonErrorDiv2').css('display','none');
	$('#blackListNameTableBody').empty();
	$('#txt-private-blacklist-name-add').val('');
	$('#txt-private-blacklist-name-search').val('');
	$('#timePicker1').val('');
	$('#timePicker2').val('');
	$('#textAreaFields').val('');
	$('#SelectedModule').val('Registration');
	$('#barDIV').css('display','none');
	$(' #blacklist, #fraugster, #sanction, #customCheck, #eid').css('visibility','hidden');
	enableButton('fraugster-count-search');
	disableButton('fraudpredict-repeat');
	disableButton('force-clear-data');
	disableButton('refresh-count'); //AT-4355
	closeMessageDivs();
}
 function closeMessageDivs(){
	 $("#successDiv").css('display','none');
	 $("#errorDiv").css('display','none');
	 $("#successDeleteDiv").css('display', 'none');
	 $("#errorDeleteDiv").css('display', 'none');
}

function addBlackListData(){
	setAdministrationPermission();
	closeMessageDivs();
	var request = {};
	var dataType = $("#addBlackListType").val();
	var blacklistDataType = dataType;
	var inputToAdd = $('#txt-private-blacklist-name-add').val();
	var data = [{"type" : dataType ,"value" : inputToAdd }];
	var blacklistRequest = {"data" : data , "createdBy" : getUserObject().id , "updatedBy" : getUserObject().id };
	addField('blacklistDataType', blacklistDataType, request);
	addField('blacklistRequest', blacklistRequest, request);
	if($.trim(inputToAdd) === "" || $.trim(inputToAdd).length === 0 ) {
		$("#emptyErrorDiv").css('display', 'block');		
	} 
	else {
		$.ajax({
			url : '/internalrule-service/saveIntoBlacklistData',
			type : 'POST',
			data : getJsonString(request),
			contentType : "application/json",
			success : function(response) {
				if(response.status == 'Success'){
					$("#emptyErrorDiv").css('display', 'none');
					$("#successDiv").css('display', 'block');
					$('#txt-private-blacklist-name-add').val('');
					$('#blackListNameTableBody').empty();
					showBlackListNames(dataType);
				}else if (response.status == 'Fail'){
					$("#emptyErrorDiv").css('display', 'none');
					$("#errorDescription").text(response.errorDescription);
					$("#errorDiv").css('display', 'block');
				}
			},
			error : function() {
				alert('Error while fetching addBlackListData');
			}
		});
	}
}

function getUserObject() {
	return getJsonObject($('#userInfo').val());
}

function searchBlackListData(){
	
	closeMessageDivs();
	var dataType = $("#searchBlackListType").val();
	var request = {};
	var blacklistDataType = dataType;
	var inputToAdd = $('#txt-private-blacklist-name-search').val();
	var data = [{"type" : dataType ,"value" : inputToAdd }];
	var blacklistRequest = {"data" : data};
	addField('blacklistDataType', blacklistDataType, request);
	addField('blacklistRequest', blacklistRequest, request);
	if($.trim(inputToAdd) === "" || $.trim(inputToAdd).length === 0 ) {
		$("#emptyErrorDiv").css('display', 'block');
	} 
	else {
		$.ajax({
			url : '/internalrule-service/searchBlacklistData',
			type : 'POST',
			data : getJsonString(request),
			contentType : "application/json",
			success : function(response) {
				if(response.status == 'Success'){
					$('#blackListNameTableBody').empty();
					showBlackListDataResponse(response.blacklistDataResponse);
					$('#txt-private-blacklist-name-search').val('');
				}else if (response.status == 'Fail'){
					$("#emptyErrorDiv").css('display', 'none');
					$("#errorDiv").css('display', 'block');
				}
			},
			error : function() {
				alert('Error while fetching searchBlackListData');
			}
		});
	}
}

function deleteBlackListData(dataType,createdDate,inputToAdd){
	
	closeMessageDivs();
	var request = {};
	var blacklistDataType = dataType;
	var data = [{"type" : dataType ,"value" : inputToAdd }];
	var blacklistRequest = {"data" : data , "createdBy" : getUserObject().id , "updatedBy" : getUserObject().id };
	addField('blacklistDataType', blacklistDataType, request);
	addField('blacklistRequest', blacklistRequest, request);
	if(confirm('Are You Sure You Want To Delete This')){
	$.ajax({
		url : '/internalrule-service/deleteFromBlacklist',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(response) {
			if(response.status == 'Success'){
				$("#emptyErrorDiv").css('display', 'none');
				$("#successDeleteDiv").css('display', 'block');
				$('#blackListNameTableBody').empty();
				showBlackListNames(dataType);
			}else if (response.status == 'Fail'){
				$("#emptyErrorDiv").css('display', 'none');
				$("#errorDeleteDiv").css('display', 'block');
			}
		},
		error : function() {
			alert('Error while fetching deleteBlackListData');
		}
	});
   }
}

function changeLabelDynamically(dataType) {
	var label = '';
	if (dataType === 'PHONE_NUMBER') {
		label = 'Add phone number';
	} else if (dataType === 'Name') {
		label = 'Add name';
	} else if (dataType === 'Email') {
		label = 'Add email';
	} else if (dataType === 'DOMAIN') {
		label = 'Add domain';
	} else if (dataType === 'IPADDRESS') {
		label = 'Add ip address';
	} else if (dataType === 'ACC_NUMBER') {
		label = 'Add account number';
	} 
	$('#txt-private-blacklist-name-add-label-id').text(label);
}


function addingBlackListDataPopup () {
	$("#addBlacklist").css('display', 'block');
	$("#popupHeadingAddBlacklist").text("Add Blacklist Data");
	$("#txt-blacklist-name-add").val('');
	$("#txt-blacklist-email-add").val('');
	$("#txt-blacklist-domain-add").val('');
	$("#txt-blacklist-ip-add").val('');
	$("#txt-blacklist-accNum-add").val('');
	$("#txt-blacklist-tel-add").val('');
	$("#txt-blacklist-notes-add").val('');
	$("#allBlacklistEmptyErrorDiv").css('display', 'none');
	$("#allBlacklistErrorDiv").css('display', 'none');
	$("#allBlacklistSuccessDiv").css('display', 'none');
	
}

function addMultipleBlacklistData() {
	closeMessageDivs();
	$('#gifloaderforblacklistaddition').css('visibility','visible');
	var name = $("#txt-blacklist-name-add").val();
	var email = $("#txt-blacklist-email-add").val();
	var domain = $("#txt-blacklist-domain-add").val();
	var ipAddress = $("#txt-blacklist-ip-add").val();
	var accountNumber = $("#txt-blacklist-accNum-add").val();
	var tel = $("#txt-blacklist-tel-add").val();
	var notes = $("#txt-blacklist-notes-add").val();
	
	
	var request = {};
	
	var data = [];
	
	if(!getIsEmptyOrNull($.trim(name))) {
		data.push({"type" : "NAME" ,"value" : name })
	}
	if(!getIsEmptyOrNull($.trim(email))) {
		data.push({"type" : "EMAIL" ,"value" : email })
	}
	if(!getIsEmptyOrNull($.trim(domain))) {
		data.push({"type" : "DOMAIN" ,"value" : domain })
	}
	if(!getIsEmptyOrNull($.trim(ipAddress))) {
		data.push({"type" : "IPADDRESS" ,"value" : ipAddress })
	}
	if(!getIsEmptyOrNull($.trim(accountNumber))) {
		data.push({"type" : "ACC_NUMBER" ,"value" : accountNumber })
	}
	if(!getIsEmptyOrNull($.trim(tel))) {
		data.push({"type" : "PHONE_NUMBER" ,"value" : tel })
	}
	if(getIsEmptyOrNull($.trim(notes))) {
		notes = null;
	}
	
	var blacklistRequest = {"data" : data , "createdBy" : getUserObject().id , "updatedBy" : getUserObject().id, "notes": notes};
	addField('blacklistRequest', blacklistRequest, request);
		
	if(data.length === 0) {
		$('#gifloaderforblacklistaddition').css('visibility','hidden');
		$("#allBlacklistSuccessDiv").css('display', 'none');
		$("#allBlacklistErrorDiv").css('display', 'none');
		$("#allBlacklistEmptyErrorDiv").css('display', 'block');
		$("#txt-blacklist-name-add").val('');
		$("#txt-blacklist-email-add").val('');
		$("#txt-blacklist-domain-add").val('');
		$("#txt-blacklist-ip-add").val('');
		$("#txt-blacklist-accNum-add").val('');
		$("#txt-blacklist-tel-add").val('');
		$("#txt-blacklist-notes-add").val('');
	}
	else {
		$.ajax({
			url : '/internalrule-service/saveIntoBlacklistData',
			type : 'POST',
			data : getJsonString(request),
			contentType : "application/json",
			success : function(response) {
				if(response.status == 'Success'){
					$('#gifloaderforblacklistaddition').css('visibility','hidden');
					$("#allBlacklistEmptyErrorDiv").css('display', 'none');
					$("#allBlacklistErrorDiv").css('display', 'none');
					$("#allBlacklistSuccessDiv").css('display', 'block');
					$("#txt-blacklist-name-add").val('');
					$("#txt-blacklist-email-add").val('');
					$("#txt-blacklist-domain-add").val('');
					$("#txt-blacklist-ip-add").val('');
					$("#txt-blacklist-accNum-add").val('');
					$("#txt-blacklist-tel-add").val('');
					$("#txt-blacklist-notes-add").val('');
				}else if (response.status == 'Fail'){
					$('#gifloaderforblacklistaddition').css('visibility','hidden');
					$("#allBlacklistEmptyErrorDiv").css('display', 'none');
					$("#allBlacklistSuccessDiv").css('display', 'none');
					$("#allBlacklistErrorDescription").text(response.errorDescription+", Please try again");
					$("#allBlacklistErrorDiv").css('display', 'block');
				}
			},
			error : function() {
				$('#gifloaderforblacklistaddition').css('visibility','hidden');
				alert('Error while adding multiple BlackListData');
			}
		});
	}
}

function showWhitelistBeneficiaryNames() {
	setAdministrationPermission();
	$("#modal-private-name-whitelist").css('display', 'block');
	$("#popupHeading").text('');
	var request = {};
	$.ajax({
		url : '/internalrule-service/showWhiteListBeneficiaryData',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			showWhiteListBeneficiaryDataResponse(data);
		},
		error : function() {
			alert('Error while fetching data');
		}
	});
}

function showWhiteListBeneficiaryDataResponse(data) {
	var jsonString = data;
	var table;
	var user = getJsonObject(getValueById('userInfo'));
	var permissions = user.permissions;
	if(null != jsonString && jsonString != ''){
		for(var i=0; i<jsonString.length; i++){
			if (permissions.canNotLockAccount) {
			table += '<tr><td class="small-cell"><a href="#" onclick = "deletewhiteListBeneficiaryData(\''+jsonString[i].accountNumber+'\',\''+jsonString[i].createdOn+'\' ,\''+jsonString[i].firstName+' \')" </a></td>';
			} else {
			table += '<tr><td class="small-cell"><a href="#" onclick = "deletewhiteListBeneficiaryData(\''+jsonString[i].accountNumber+'\',\''+jsonString[i].createdOn+'\' ,\''+jsonString[i].firstName+' \')" class="material-icons">close</a></td>';
			}
			table += '<td style="width: 200px;">' + jsonString[i].createdOn + '</td>';
			table += '<td>' + jsonString[i].firstName + '</td>';
			table += '<td class = "breakword" style="width: 200px;">' + jsonString[i].accountNumber + '</td>';
		}	
	}else{
		table += '<tr><td></td>';
		table += '<td style="width: 200px;"> No data found </td></tr>';
	}
	
	$('#whiteListBeneficiaryNameTableBody').empty();
	$('#whiteListBeneficiaryNameTableBody').append(table);
}

function addWhitelistBeneficiaryData(){
	
	closeMessageDivs(); 
	var firstName = $('#txt-private-whitelistbeneficiary-name-add').val();
	var accountNumber = $('#txt-private-whitelistbeneficiary-actNum-add').val();
	var whitelistDataRequest = {"firstName" : firstName, "accountNumber" : accountNumber , "createdBy" : getUserObject().id };
	$('#txt-private-WhitelistBeneficiary-name-search').val('');
	
	if($.trim(firstName) === "" || $.trim(firstName).length === 0 && $.trim(accountNumber) === "" || $.trim(accountNumber).length === 0) {
		$("#whitelistEmptyErrorDiv").css('display', 'block');
		$("#whitelistSuccessDiv").css('display', 'none');
		$("#whitelistErrorDiv").css('display', 'none');
		$("#whitelistSuccessDeleteDiv").css('display', 'none');
		$("#whitelistErrorDeleteDiv").css('display', 'none');
	} 
	else {
		$.ajax({
			url : '/internalrule-service/saveIntoWhitelistBeneficiaryData',
			type : 'POST',
			data : getJsonString(whitelistDataRequest),
			contentType : "application/json",
			success : function(response) {
				if(response.status == 'Success'){
					$("#whitelistEmptyErrorDiv").css('display', 'none');
					$("#whitelistErrorDiv").css('display', 'none');
					$("#whitelistSuccessDeleteDiv").css('display', 'none');
					$("#whitelistErrorDeleteDiv").css('display', 'none');
					$("#whitelistSuccessDiv").css('display', 'block');
					$('#txt-private-whitelistbeneficiary-name-add').val('');
					$('#txt-private-whitelistbeneficiary-actNum-add').val('');
					$('#whiteListBeneficiaryNameTableBody').empty();
					$('#txt-private-whitelistbeneficiary-name-add').val('');
					$('#txt-private-whitelistbeneficiary-actNum-add').val('');
					showWhitelistBeneficiaryNames();
				}else if (response.status == 'Fail'){
					$("#whitelistEmptyErrorDiv").css('display', 'none');
					$("#whitelistSuccessDeleteDiv").css('display', 'none');
					$("#whitelistErrorDeleteDiv").css('display', 'none');
					$("#whitelistSuccessDiv").css('display', 'none');
					$("#whitelistErrorDescription").text(response.errorDescription);
					$("#whitelistErrorDiv").css('display', 'block');
				}
			},
			error : function() {
				alert('Error while fetching addWhitelistData');
			}
		});
	}
}

function deletewhiteListBeneficiaryData(accountNumber,createdDate,firstName){
	$('#txt-private-whitelistbeneficiary-name-add').val('');
	$('#txt-private-whitelistbeneficiary-actNum-add').val('');
	closeMessageDivs();
	var whitelistBeneficiaryRequest = {"accountNumber" : accountNumber , "firstName" : firstName};
	if(confirm('Are You Sure You Want To Delete This')){
	$.ajax({
		url : '/internalrule-service/deleteFromWhiteListBeneficiary',
		type : 'POST',
		data : getJsonString(whitelistBeneficiaryRequest),
		contentType : "application/json",
		success : function(response) {
			if(response.status == 'Success'){
				$("#whitelistEmptyErrorDiv").css('display', 'none');
				$("#whitelistSuccessDeleteDiv").css('display', 'block');
				$("#whitelistSuccessDiv").css('display', 'none');
				$("#whitelistErrorDiv").css('display', 'none');
				$("#whitelistErrorDeleteDiv").css('display', 'none');
				$('#whiteListBeneficiaryNameTableBody').empty();
				$('#txt-private-WhitelistBeneficiary-name-search').val('');
				showWhitelistBeneficiaryNames();
			}else if (response.status == 'Fail'){
				$("#whitelistEmptyErrorDiv").css('display', 'none');
				$("#whitelistSuccessDiv").css('display', 'none');
				$("#whitelistErrorDiv").css('display', 'none');
				$("#whitelistSuccessDeleteDiv").css('display', 'none');
				$("#whitelistErrorDeleteDiv").css('display', 'block');
			}
		},
		error : function() {
			alert('Error while fetching deletewhiteListBeneficiaryData');
		}
	});
   }
}

function searchWhiteListBeneficiaryData(){
	
	closeMessageDivs();
	var firstName = $('#txt-private-WhitelistBeneficiary-name-search').val();
	var accountNumber = $('#txt-private-WhitelistBeneficiary-name-search').val();
	var whitelistBeneficiaryRequest = {"firstName" : firstName, "accountNumber" : accountNumber};
	$('#txt-private-whitelistbeneficiary-name-add').val('');
	$('#txt-private-whitelistbeneficiary-actNum-add').val('');
	if($.trim(firstName) === "" || $.trim(firstName).length === 0 ) {
		$("#whitelistEmptyErrorDiv").css('display', 'block');
		$("#whitelistSuccessDiv").css('display', 'none');
		$("#whitelistErrorDiv").css('display', 'none');
		$("#whitelistSuccessDeleteDiv").css('display', 'none');
		$("#whitelistErrorDeleteDiv").css('display', 'none');
	} 
	else {
		$.ajax({
			url : '/internalrule-service/searchWhiteListBeneficiaryData',
			type : 'POST',
			data : getJsonString(whitelistBeneficiaryRequest),
			contentType : "application/json",
			success : function(response) {
					$('#whiteListBeneficiaryNameTableBody').empty();
					showWhiteListBeneficiaryDataResponse(response);
					$("#whitelistEmptyErrorDiv").css('display', 'none');
					$("#whitelistSuccessDiv").css('display', 'none');
					$("#whitelistErrorDiv").css('display', 'none');
					$("#whitelistSuccessDeleteDiv").css('display', 'none');
					$("#whitelistErrorDeleteDiv").css('display', 'none');
					$('#txt-private-WhitelistBeneficiary-name-search').val('');
			},
			error : function() {
				alert('Error while fetching WhitelistBeneficiaryData');
			}
		});
	}
}

function closePopUpWhitelistBeneficiary(){
	$('#whiteListBeneficiaryNameTableBody').empty();
	$('#txt-private-whitelistbeneficiary-name-add').val('');
	$('#txt-private-whitelistbeneficiary-actNum-add').val('');
	$('#txt-private-WhitelistBeneficiary-name-search').val('');
	closeMessageDivsWhitelistBeneficiary();
}
 function closeMessageDivsWhitelistBeneficiary(){
	 	$("#whitelistSuccessDiv").css('display', 'none');
		$("#whitelistErrorDiv").css('display', 'none');
		$("#whitelistSuccessDeleteDiv").css('display', 'none');
		$("#whitelistErrorDeleteDiv").css('display', 'none');
		$("#whitelistEmptyErrorDiv").css('display', 'none');
}
 

 $(document).ready(function() {
 	$('#timePicker1').datetimepicker({
 		controlType : 'select',
 		timeFormat : 'HH:mm:ss',
 		oneLine : true,
 		maxDate : (new Date())
 	});
 });
 $(document).ready(function() {
 	$('#timePicker2').datetimepicker({
 		controlType : 'select',
 		timeFormat : 'HH:mm:ss',
 		oneLine : true,
 		maxDate : (new Date())
 	});
 });



 function RepeatCheckPopup() {
 	$("#repeatCheck").css('display', 'block');
 }

 function SearchServiceFailed() {
	 setAdministrationPermission();
	disableButton('fraugster-count-search');
	var divId = $('.administrationMessageDiv:visible').prop('id');
	if($('#'+divId).css('display') == 'block') {
		$('#'+divId).css('display','none');
	}
	
 	if($("#timePicker1").val()=="" || $("#timePicker2").val()=="" ){
 		enableButton('fraugster-count-search');
 		$('#dateErrorDiv').css('display','block');
 	}
 	else{
 		var dateFromYear = $("#timePicker1").val().split(' ')[0].split('/');
 		var dateToYear = $("#timePicker2").val().split(' ')[0].split('/');
 		
 		if(dateFromYear[2] < dateToYear[2]) {
 			SearchServiceFailedRecord();
 		}
 		else if(dateFromYear[2] > dateToYear[2]){
 	 		$(' #blacklist, #fraugster, #sanction, #customCheck, #eid').css('visibility','hidden');
 	 		$('#dateComparisonErrorDiv').css('display','block');
 	 		enableButton('fraugster-count-search');
 		}
 		else if($("#timePicker1").val() > $("#timePicker2").val()) {
 	 		$(' #blacklist, #fraugster, #sanction, #customCheck, #eid').css('visibility','hidden');
 	 		$('#dateComparisonErrorDiv').css('display','block');
 	 		enableButton('fraugster-count-search');
 		}
 		else {
 			SearchServiceFailedRecord();
 		}
 	}
 }
 
function SearchServiceFailedRecord() {
	var moduleName = $("#SelectedModule").val();
	 	var dateFrom = getDateTimeFormatRC($("#timePicker1").val());
	 	var dateTo = getDateTimeFormatRC($("#timePicker2").val());
	 	var request = {};
	 	 	
		addField('moduleName', moduleName, request);
		addField('dateFrom', dateFrom, request);
		addField('dateTo', dateTo, request);
	 	
	 	if(moduleName=="Registration"){
	 		regServiceFailure(request);
	 	}
	 	if(moduleName=="Payment In"){
	 		payInServiceFailure(request);
	 	}
	 	if(moduleName=="Payment Out"){
	 		payOutServiceFailure(request);
	 	}
}
 
function getDateTimeFormatRC(dateTime){
	var date = "",od="",time="",result="";
	date = dateTime.split(' ')[0].split('/');
    od = date[2] + "-" + date[0] + "-" + date[1];
    time=dateTime.split(" ")[1].split('.');
    result = od + " " +time[0];
	return result;
}

function regServiceFailure(request){
	disableButton('fraugster-count-search');
		$.ajax({
			url : '/compliance-portal/registrationServiceFailureCount',
			type : 'POST',
			data : getJsonString(request),
			contentType : "application/json",
			success : function(data) {
				showServiceFailCount(data);
			},
			error : function() {
				alert('Error while fetching data');
			}
		});	
	
}

function payInServiceFailure(request){
	disableButton('fraugster-count-search');
	$.ajax({
		url : '/compliance-portal/fundsInServiceFailureCount',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			showServiceFailCount(data);
		},
		error : function() {
			alert('Error while fetching data');
		}
	});	

}

function payOutServiceFailure(request){
	disableButton('fraugster-count-search');
	$.ajax({
		url : '/compliance-portal/fundsOutServiceFailureCount',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			showServiceFailCount(data);
		},
		error : function() {
			alert('Error while fetching data');
		}
	});	

}

function showServiceFailCount(data){
	var jsonString = data, failCountFlag = 0;
	enableAllButtons();
	setAdministrationPermission();
	if(jsonString.blacklistFailCount == 0 && jsonString.fraugsterFailCount == 0 && jsonString.sanctionFailCount == 0) {
		failCountFlag = 1;
	}
	
	$('#blacklist,#fraugster,#sanction,#customCheck,#eid').css('visibility','visible');
 	$('#blacklist').text(jsonString.blacklistFailCount + " Blacklist Failed records");
 	$('#fraugster').text(jsonString.fraugsterFailCount + " Fraugster Failed records");
 	$('#sanction').text(jsonString.sanctionFailCount + " Sanction Failed records");
	if($("#SelectedModule").val()!="Registration") {
		if(failCountFlag == 1 && jsonString.customCheckFailCount == 0) {
			disableButton('fraudpredict-repeat');
			disableButton('force-clear-data');
			disableButton('refresh-count'); //AT-4355
		}
 		$('#customCheck').text(jsonString.customCheckFailCount + " CustomCheck Failed records");
 		$('#eid').text("");
	}
	else {
		if(failCountFlag == 1 && jsonString.eidFailCount == 0) {
			disableButton('fraudpredict-repeat');
			disableButton('force-clear-data');
			disableButton('refresh-count'); //AT-4355
		}
	 	$('#eid').text(jsonString.eidFailCount + " EID Failed records");
	 	$('#customCheck').text("");
	}
}

function getServiceFailureData() {
	$("#repeatcheck-count").empty(); //AT-4289
	clearReprocessFailed();	//AT-4289
	disableAllButtons();
	var confirmation = confirm("Do you want to continue ?");
 	if(confirmation) {
 			var moduleName = $("#SelectedModule").val();
		 	var dateFrom = getDateTimeFormatRC($("#timePicker1").val());
		 	var dateTo = getDateTimeFormatRC($("#timePicker2").val());
		 	var request = {};
		 	
			addField('moduleName', moduleName, request);
			addField('dateFrom', dateFrom, request);
			addField('dateTo', dateTo, request);
			var transTypeInteger =0;
			
		 	if(moduleName=="Registration"){
		 		transTypeInteger = 1;
		 		getRegistrationData(request,transTypeInteger);
		 	}
		 	if(moduleName=="Payment In"){
		 		transTypeInteger = 2;
		 		getPaymentInData(request,transTypeInteger);
		 	}
		 	if(moduleName=="Payment Out"){
		 		transTypeInteger = 3;
		 		getPaymentOutData(request,transTypeInteger);
		 	}
		}
 	else {
 		enableAllButtons();
 	}
 }

function getPaymentInData(request,transTypeInteger){
	var user = getUser();
	disableAllButtons();
	$.ajax({
		url : '/compliance-portal/recheckFundsIn',
		type : 'POST',
		headers: {
	        "user": user
		},
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			progressbars(data,transTypeInteger);
		},
		error : function() {
			alert('Error while fetching data');
		}
	});	

}
function getRegistrationData(request,transTypeInteger){
	disableAllButtons();
	$.ajax({
		url : '/compliance-portal/recheckRegistration',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			progressbars(data,transTypeInteger);
		},
		error : function() {
			alert('Error while fetching data');
		}
	});	

}
function getPaymentOutData(request,transTypeInteger){
	disableAllButtons();
	var user = getUser();
	var deleteRequest = {};
	$.ajax({
		url : '/compliance-portal/recheckFundsOut',
		type : 'POST',
		headers: {
	        "user": user
		},
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			progressbars(data,transTypeInteger);
			
		},
		error : function() {
			alert('Error while fetching data');
		}
	});	
}

function progressbars(data,transTypeInteger) {
	disableAllButtons();
	var count = 0, batchId = 0, request = {}, statusDoneCount = 0, barWidth = 0, progressBarValue = 0, reCheckStatusFlag = 1, countStatusFlag = 0, barStatusFlag = 0, i = 0, countHolder = 0, countHolderCopy = 0, progressbar = $("#progressbar-5"), progressLabel = $("#progress-label"), timeOut;
	count = data.totalCount;
	batchId = data.batchId;
	updatedBatchId = batchId;
	$('#barDIV').css('display','block');
	if(batchId == undefined){
		$('#barDIV').css('display','none');
		$('#repeatCheckError').css('display','block');
	}
	else {		
		$("#progressbar-5").progressbar({
			value: 0,
			change: function() {
				progressLabel.html("In Progress..");
				disableAllButtons();
			},
			complete : function() {
				progressLabel.html("Completed");
				countHolderCopy = 0;
				$('#repeatCheckComplete').css('display','block');
				enableAllButtons();
				//setTimeout(function() { showCount(batchId); }, 5000); //AT-4289
			}
		});
		
		$(".ui-widget-header").css('background-color','#348cd2');
		$(".ui-progressbar .ui-progressbar-value").css('margin','0px');

		barWidth = 100/count;
		document.getElementById("total-count").innerHTML = count;
		
		function progress() {
			addField('batchId', batchId, request);
			statusDoneCount = getDataForProgressBar(request);
			countHolder = statusDoneCount.responseJSON;
			countHolderCopy = countHolder;
			countStatusFlag = countHolderCopy;
			document.getElementById("done-count").innerHTML = countHolderCopy;
			
			if(barStatusFlag < countStatusFlag) {
				for(i  = 0 ; barStatusFlag < countStatusFlag ; i++) {
					progressBarValue = progressBarValue + barWidth;
					progressbar.progressbar("value", progressBarValue);
					barStatusFlag++;
				}
				if(progressBarValue < 100) {
					timeOutObject = setTimeout(progress,200);
				}
			}
			else {
				timeOutObject = setTimeout(progress,500);
			}
		}
		timeOutObject = setTimeout(progress,500);
	}
}

function getDataForProgressBar(request) {
	return $.ajax({
		url : '/compliance-portal/repeatCheckProgressBar',
		type: 'POST',
		async: false,
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			return data;
		},
		error : function() {
			alert('Error while fetching data');
		}
	});
}

function forceClearData() {
	disableAllButtons();
	var confirmation = confirm("Do you want to continue ?");
	if(confirmation) {
		var moduleName = $("#SelectedModule").val();
 	 	
	 	var dateFrom = getDateTimeFormatRC($("#timePicker1").val());
	  	var dateTo = getDateTimeFormatRC($("#timePicker2").val());
	 	var request = {};
	 	
		addField('moduleName', moduleName, request);
		addField('dateFrom', dateFrom, request);
		addField('dateTo', dateTo, request);
	 	
	 	if(moduleName=="Registration"){
	 		forceClearRegistration(request);
	 	}
	 	if(moduleName=="Payment In"){
	 		forceClearPaymentIn(request);
	 	}
	 	if(moduleName=="Payment Out"){
	 		forceClearPaymentOut(request);
	 	}
	}
	else {
 		enableAllButtons();
 	}
 }

function forceClearPaymentOut(request){
	disableAllButtons();
	var user = getUser();
	$.ajax({
		url : '/compliance-portal/forceClearFundsOuts',
		type : 'POST',
		headers: {
	        "user": user
		},
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			payOutServiceFailure(request);
			$('#repeatForceCheckComplete').css('display','block');
		},
		error : function() {
			alert('Error while fetching data');
		}
	});	
}

function forceClearPaymentIn(request){
	disableAllButtons();
	var user = getUser();
	$.ajax({
		url : '/compliance-portal/forceClearFundsIn',
		type : 'POST',
		headers: {
	        "user": user
		},
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			payInServiceFailure(request);
			$('#repeatForceCheckComplete').css('display','block');
		},
		error : function() {
			alert('Error while fetching data');
		}
	});	
}

function deleteReprocessFailedTable(batchId,transTypeInteger){
	var deleteRequest = {};
	addField('batchId', batchId, deleteRequest);
	addField('transTypeInteger',transTypeInteger,deleteRequest);
	deleteReprocessFailed(deleteRequest);
}

function deleteReprocessFailed(request) {
	$.ajax({
		url : '/compliance-portal/deleteReprocessFailed',
		type : 'POST',
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			if(data != 0){
				return data;
			}
		},
		error : function() {
			console.log("data not deleted");
		}
	});
}

function unlockAdministrationResource(){
	disableButton('add_button');
	disableButton('Whitelist_add_button');
	disableButton('Add_to_blacklist');
	disableButton('fraudpredict-repeat');
	disableButton('force-clear-data');
	disableButton('fraudpredict-repeat');
	disableButton('force-clear-data');
	disableButton('refresh-count'); //AT-4355
}

function setAdministrationPermission(){
	var user = getJsonObject(getValueById('userInfo'));
	var permissions = user.permissions;
	if (permissions.canNotLockAccount) {
		unlockAdministrationResource();
	}
}

//Add For AT-4185
function updateTMMQRetryCount() {
	setAdministrationPermission();
	var request = {};
	if (confirm('Are you sure you want to update TMMQ retry count')) {
		$.ajax({
			url: '/compliance-portal/updateTMMQRetryCount',
			type: 'POST',
			data: getJsonString(request),
			contentType: "application/json",
			success: function(data) {
				alert('Successfully updated Transaction Monitoring retry count data');
				document.location.reload();
			},
			error: function() {
				alert('Error while updating data');
			}
		});
	}
}

//AT-4289
function showCount(){
	$.ajax({
		url : '/compliance-portal/showCountReprocessFailed',
		data:"batchId="+updatedBatchId,
		success : function(data) {
			$("#repeatcheck-count").empty();
			var countArray = JSON.parse(data);
			var countDiv = '';
			for(var i=0; i<countArray.length;i++){
				var status = countArray[i].Status;
				var count = countArray[i].count;
				countDiv += "<span style='padding: 0px 5px 0px 10px;'>"+status+"</span><span>"+count+"</span>";
			}
			$("#repeatcheck-count").append(countDiv);
			//deleteReprocessFailedTable(batchId,1);
		},
		error : function() {
			console.log("Error in show count");
		}
	});
}

//AT-4289
function clearReprocessFailed() {
	$.ajax({
		url : '/compliance-portal/clearReprocessFailed',
		success : function(data) {
			if(data != 0){
				return data;
			}
		},
		error : function() {
			console.log("data not deleted");
		}
	});
}


//AT-4773
function regSyncIntuitionPopUp(){
		$("#syncRegCheck").css('display', 'block');
}


function regSyncWithIntuition(){
	var flag =true;
	var txtFields=$("#textAreaFields").val();
	var formattedFields = txtFields.replaceAll(" ",'');
	var fieldsArray=formattedFields.split(",");
	if (fieldsArray.length < 50 && fieldsArray[0] != '' && fieldsArray[fieldsArray.length - 1] != '') {
		storeRegSyncToTMMQ(fieldsArray);
		var txtFields = $("#textAreaFields").val('');
		return flag;
	} else if (fieldsArray[0] == '') {
		setNullError();
		flag = false;
	} else if (fieldsArray[fieldsArray.length - 1] == '') {
		setLastElementNullError();
	} else {
		setError();
		flag = false;
	}
	
	return flag;
}

function storeRegSyncToTMMQ(request) {
	var user = getUser();
	$.ajax({
		url : '/compliance-portal/syncRegWithIntuition',
		type : 'POST',
		headers: {
	        "user": user
		},
		data : getJsonString(request),
		contentType : "application/json",
		success : function(data) {
			alert("Registration data sync successfully");
			
		},
		error : function() {
			alert("Something went wrong");
		}
	});
}

function setError(){
	alert("You've entered more than 50 trade account numbers");
	var txtFields=$("#textAreaFields").val('');	
}

function setNullError(){
	alert("Please Enter trade account numbers fields can't be null");
	var txtFields=$("#textAreaFields").val('');	
}
function setLastElementNullError(){
	alert("Invalid input");
	var txtFields=$("#textAreaFields").val('');	
}

//Add For AT-5023
function updatePCTMQRetryCount() {
	setAdministrationPermission();
	var request = {};
	if (confirm('Are you sure you want to update PostCard MQ retry count')) {
		$.ajax({
			url: '/compliance-portal/updatePostCardTMMQRetryCount',
			type: 'POST',
			data: getJsonString(request),
			contentType: "application/json",
			success: function(data) {
				alert('Successfully updated PostCard Repeat Check retry count data');
				document.location.reload();
			},
			error: function() {
				alert('Error while updating data');
			}
		});
	}
}
