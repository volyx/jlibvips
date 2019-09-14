package org.jlibvips.operations;

import com.sun.jna.Pointer;
import org.jlibvips.VipsImage;
import org.jlibvips.VipsPrecision;
import org.jlibvips.exceptions.VipsException;
import org.jlibvips.jna.VipsBindingsSingleton;
import org.jlibvips.util.Varargs;

import static org.jlibvips.util.VipsUtils.toOrdinal;

public class VipsGaussBlurOperation {

	private final Pointer in;
	private final double sigma;
	private VipsPrecision precision;
	private double min_ampl = 0.2;

	public VipsGaussBlurOperation(Pointer in, double sigma) {
		this.in = in;
		this.sigma = sigma;
	}

	public VipsImage create() {
		Pointer[] out = new Pointer[1];
		int ret = VipsBindingsSingleton.instance().vips_gaussblur(this.in, out, sigma,
				new Varargs()
						.add("precision", toOrdinal(precision))
						.add("min_ampl", min_ampl)
						.toArray());
		if(ret != 0) {
			throw new VipsException("vips_gaussblur", ret);
		}
		return new VipsImage(out[0]);
	}

	public VipsGaussBlurOperation precision(VipsPrecision precision) {
		this.precision = precision;
		return this;
	}

	public VipsGaussBlurOperation minAmplitude(double min_ampl) {
		this.min_ampl = min_ampl;
		return this;
	}
}
