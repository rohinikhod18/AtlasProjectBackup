CDPage_Profile = {

	initCompliance:function() {

		var self = this;

		// AMOUNT RANGE TOLERANCE ***********

		$('.slider-amr .slider__widget').slider(

			{
				value	: 0,
				min 	: 100,
				max 	: 1000,
				step 	: 10,
				slide: function(event,ui) {
					$(this).parent().siblings('.slider__amount').text(ui.value+'%');
					$(this).siblings('.slider__values').find('.field-slider__amount').val(ui.value);
				}
			}

		);

		$('.slider-amr').each(function() {
			$(this).find('.slider__amount').text($(this).find('.slider__widget').slider('value')+'%');
			$(this).find('.field-slider__amount').val($(this).find('.slider__widget').slider('value'));
		});






		// FRAUGSTER THRESHOLD ***********

		$('.slider-fraugster-threshold .slider__widget').slider(

			{
				range 	: true,
				values	: [0,1.0],
				min 	: 0.0,
				max 	: 1.0,
				step 	: 0.01,
				slide: function(event,ui) {
					$(this).parent().siblings('.slider__amount').text(ui.values[0].toFixed(2)+'-'+ui.values[1].toFixed(2));
					$(this).siblings('.slider__values').find('.field-slider__amount-from').val(ui.values[0]);
					$(this).siblings('.slider__values').find('.field-slider__amount-to').val(ui.values[1]);
				}
			}

		);

		$('.slider-fraugster-threshold').each(function() {
			$(this).find('.slider__amount').text($(this).find('.slider__widget').slider('values',0).toFixed(2) + '-' + $(this).find('.slider__widget').slider('values',1).toFixed(2));
			$(this).find('.field-slider__amount-from').val($(this).find('.slider__widget').slider('values',0));
			$(this).find('.field-slider__amount-to').val($(this).find('.slider__widget').slider('values',1));
		});






	}

};

CDPage_Profile.initCompliance();