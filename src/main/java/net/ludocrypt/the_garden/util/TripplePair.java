package net.ludocrypt.the_garden.util;

import java.util.HashMap;

import com.google.common.collect.Maps;

public class TripplePair<A, B, C> {

	private final HashMap<A, B> AB_SIDE = Maps.newHashMap();
	private final HashMap<A, C> AC_SIDE = Maps.newHashMap();
	private final HashMap<B, A> BA_SIDE = Maps.newHashMap();
	private final HashMap<B, C> BC_SIDE = Maps.newHashMap();
	private final HashMap<C, A> CA_SIDE = Maps.newHashMap();
	private final HashMap<C, B> CB_SIDE = Maps.newHashMap();

	public TripplePair<A, B, C> put(A a, B b, C c) {
		AB_SIDE.put(a, b);
		AC_SIDE.put(a, c);
		BA_SIDE.put(b, a);
		BC_SIDE.put(b, c);
		CA_SIDE.put(c, a);
		CB_SIDE.put(c, b);
		return this;
	}

	public A getAFromB(B b) {
		return BA_SIDE.get(b);
	}

	public A getAFromC(C c) {
		return CA_SIDE.get(c);
	}

	public B getBFromA(A a) {
		return AB_SIDE.get(a);
	}

	public B getBFromC(C c) {
		return CB_SIDE.get(c);
	}

	public C getCFromA(A a) {
		return AC_SIDE.get(a);
	}

	public C getCFromB(B b) {
		return BC_SIDE.get(b);
	}

	public HashMap<A, B> getAB_SIDE() {
		return AB_SIDE;
	}

	public HashMap<A, C> getAC_SIDE() {
		return AC_SIDE;
	}

	public HashMap<B, A> getBA_SIDE() {
		return BA_SIDE;
	}

	public HashMap<B, C> getBC_SIDE() {
		return BC_SIDE;
	}

	public HashMap<C, A> getCA_SIDE() {
		return CA_SIDE;
	}

	public HashMap<C, B> getCB_SIDE() {
		return CB_SIDE;
	}

	public static <A, B, C> TripplePair<A, B, C> newTripplePair() {
		return new TripplePair<A, B, C>();
	}

}
