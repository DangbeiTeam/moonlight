package com.dangbei.moonlight.framework.interpolator;

import android.graphics.PointF;
import android.view.animation.Interpolator;

/**
 * 贝塞尔曲线
 */
public class CubicBezierInterpolator implements Interpolator {

    /* renamed from: a */
    protected PointF f3408a;

    /* renamed from: b */
    protected PointF f3409b;

    /* renamed from: c */
    protected PointF f3410c;
    protected PointF end;
    protected PointF start;

    public CubicBezierInterpolator(PointF pointF, PointF pointF2) throws IllegalArgumentException {
        this.f3408a = new PointF();
        this.f3409b = new PointF();
        this.f3410c = new PointF();
        if (pointF.x < 0.0f || pointF.x > 1.0f) {
            throw new IllegalArgumentException("startX value must be in the range [0, 1]");
        } else if (pointF2.x < 0.0f || pointF2.x > 1.0f) {
            throw new IllegalArgumentException("endX value must be in the range [0, 1]");
        } else {
            this.start = pointF;
            this.end = pointF2;
        }
    }

    public CubicBezierInterpolator(float f, float f2, float f3, float f4) {
        this(new PointF(f, f2), new PointF(f3, f4));
    }

    public CubicBezierInterpolator(double d, double d2, double d3, double d4) {
        this((float) d, (float) d2, (float) d3, (float) d4);
    }

    @Override // android.animation.TimeInterpolator
    public float getInterpolation(float f) {
        return getBezierCoordinateY(getXForTime(f));
    }

    protected float getBezierCoordinateY(float f) {
        this.f3410c.y = this.start.y * 3.0f;
        this.f3409b.y = ((this.end.y - this.start.y) * 3.0f) - this.f3410c.y;
        this.f3408a.y = (1.0f - this.f3410c.y) - this.f3409b.y;
        return f * (this.f3410c.y + ((this.f3409b.y + (this.f3408a.y * f)) * f));
    }

    protected float getXForTime(float f) {
        float f2 = f;
        for (int i = 1; i < 20; i++) {
            float bezierCoordinateX = getBezierCoordinateX(f2) - f;
            if (((double) Math.abs(bezierCoordinateX)) < 0.001d) {
                break;
            }
            f2 -= bezierCoordinateX / getXDerivate(f2);
        }
        return f2;
    }

    private float getXDerivate(float f) {
        return this.f3410c.x + (f * ((this.f3409b.x * 2.0f) + (this.f3408a.x * 3.0f * f)));
    }

    private float getBezierCoordinateX(float f) {
        this.f3410c.x = this.start.x * 3.0f;
        this.f3409b.x = ((this.end.x - this.start.x) * 3.0f) - this.f3410c.x;
        this.f3408a.x = (1.0f - this.f3410c.x) - this.f3409b.x;
        return f * (this.f3410c.x + ((this.f3409b.x + (this.f3408a.x * f)) * f));
    }
}
