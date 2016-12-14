/**
 * Created by Giovanni on 07/12/16.
 */
var mBlock = document.getElementById('block'),
    mBigSmile = document.getElementById('big-smile'),
    mSmile = document.getElementById('smile'),
    mNormal=document.getElementById('normalSmile'),
    mSad=document.getElementById('sad'),
    mOoh = document.getElementById('ooh'),
    mLeftPupil = document.getElementById('left-pupil'),
    mRightPupil = document.getElementById('right-pupil');

// visibility and animation

var mHappyAnimation;
var mCurrentState;

//set state of the mouth
function setMouth(state) {
    TweenMax.set([mBigSmile, mSmile, mOoh,mSad,mNormal], {visibility:'hidden'});
    TweenMax.set(state, {visibility:'visible'});
    mCurrentState=state;

}

function setPupilAngle(angle) {
    var x = Math.cos(angle * (Math.PI / 180)) * 10,
        y = Math.sin(angle * (Math.PI / 180)) * 10;

    TweenMax.to(mLeftPupil, 0.25, {x:x, y:y});
    TweenMax.to(mRightPupil, 0.25, {x:x, y:y});
}

function happy() {
    mHappyAnimation = new TimelineMax();

    mHappyAnimation.set(mBlock, {transformOrigin:'center 80%'});
    mHappyAnimation.to(mLeftPupil, 0.2, {x:0, y:0}, 0);
    mHappyAnimation.to(mRightPupil, 0.2, {x:0, y:0}, 0);
    mHappyAnimation.call(setMouth, [mBigSmile]);

    for (var i = 0; i < 2; i++) {
        mHappyAnimation.to(mBlock, 0.3, {rotation:10, ease:Back.easeOut});
        mHappyAnimation.to(mBlock, 0.3, {rotation:-10, ease:Back.easeOut});
    }

    mHappyAnimation.to(mBlock, 0.3, {rotation:0, ease:Back.easeOut});

    mHappyAnimation.call(setMouth, [mSmile]);
}


function sad() {
    mHappyAnimation = new TimelineMax();

    mHappyAnimation.set(mBlock, {transformOrigin:'center 80%'});
    mHappyAnimation.to(mLeftPupil, 0.2, {x:0, y:0}, 0);
    mHappyAnimation.to(mRightPupil, 0.2, {x:0, y:0}, 0);
    mHappyAnimation.call(setMouth, [mSad]);

    for (var i = 0; i < 2; i++) {
        mHappyAnimation.to(mBlock, 0.3, {rotation:10, ease:Back.easeOut});
        mHappyAnimation.to(mBlock, 0.3, {rotation:-10, ease:Back.easeOut});
    }

    mHappyAnimation.to(mBlock, 0.3, {rotation:0, ease:Back.easeOut});

    mHappyAnimation.call(setMouth, [mSad]);
}
function animToEmo(state) {
    mHappyAnimation = new TimelineMax();

    mHappyAnimation.set(mBlock, {transformOrigin:'center 80%'});
    mHappyAnimation.to(mLeftPupil, 0.2, {x:0, y:0}, 0);
    mHappyAnimation.to(mRightPupil, 0.2, {x:0, y:0}, 0);
    mHappyAnimation.call(setMouth, [state]);

    for (var i = 0; i < 2; i++) {
        mHappyAnimation.to(mBlock, 0.3, {rotation:10, ease:Back.easeOut});
        mHappyAnimation.to(mBlock, 0.3, {rotation:-10, ease:Back.easeOut});
    }

    mHappyAnimation.to(mBlock, 0.3, {rotation:0, ease:Back.easeOut});

    mHappyAnimation.call(setMouth, [state]);
}
function animTo() {
    mHappyAnimation = new TimelineMax();
    mHappyAnimation.call(setMouth, [mCurrentState]);
    mHappyAnimation.set(mBlock, {transformOrigin:'center 80%'});
    mHappyAnimation.to(mLeftPupil, 0.2, {x:0, y:0}, 0);
    mHappyAnimation.to(mRightPupil, 0.2, {x:0, y:0}, 0);


    for (var i = 0; i < 2; i++) {
        mHappyAnimation.to(mBlock, 0.3, {rotation:10, ease:Back.easeOut});
        mHappyAnimation.to(mBlock, 0.3, {rotation:-10, ease:Back.easeOut});
    }

    mHappyAnimation.to(mBlock, 0.3, {rotation:0, ease:Back.easeOut});

    mHappyAnimation.call(setMouth, [mCurrentState]);
}


function superHappy() {
    mHappyAnimation = new TimelineMax();

    mHappyAnimation.set(mBlock, {transformOrigin:'center center'});
    mHappyAnimation.to(mLeftPupil, 0.2, {x:0, y:0}, 0);
    mHappyAnimation.to(mRightPupil, 0.2, {x:0, y:0}, 0);
    mHappyAnimation.call(setMouth, [mBigSmile]);

    mHappyAnimation.add('spin-out');
    mHappyAnimation.to(mBlock, 1.5, {rotation:370, ease:Back.easeInOut}, 'spin-out');
    mHappyAnimation.to(mBlock, 1.5, {scale:0.75, ease:Back.easeInOut}, 'spin-out');
    mHappyAnimation.add('spin-in');
    mHappyAnimation.to(mBlock, 1.0, {rotation:360, ease:Elastic.easeOut}, 'spin-in+=0.1');
    mHappyAnimation.to(mBlock, 1.0, {scale:1.0, ease:Elastic.easeOut}, 'spin-in+=0.1');
    mHappyAnimation.set(mBlock, {rotation:0});
    mHappyAnimation.call(setMouth, [mSmile]);
}

// gui

var mGui = new dat.GUI(),
    mSettings = {
        'borderTopLeftRadius':10,
        'borderTopRightRadius':10,
        'borderBottomRightRadius':10,
        'borderBottomLeftRadius':10
    },
    mAngles = {
        'borderTopLeftRadius':-130,
        'borderTopRightRadius':-50,
        'borderBottomRightRadius':50,
        'borderBottomLeftRadius':130
    };

var mChangeTID = -1,
    mChangeStarted = false;

mGui.add(mSettings, 'borderTopLeftRadius', 0, 100).name('top left').onChange(function() {update('borderTopLeftRadius')});
mGui.add(mSettings, 'borderTopRightRadius', 0, 100).name('top right').onChange(function() {update('borderTopRightRadius')});
mGui.add(mSettings, 'borderBottomRightRadius', 0, 100).name('bottom right').onChange(function() {update('borderBottomRightRadius')});
mGui.add(mSettings, 'borderBottomLeftRadius', 0, 100).name('bottom left').onChange(function() {update('borderBottomLeftRadius')});

function update(key) {
    mHappyAnimation && mHappyAnimation.kill();

    mBlock.style[key] = mSettings[key] + '%';

    if (!mChangeStarted) {
        mChangeStarted = true;
        setPupilAngle(mAngles[key]);
    }

    setMouth(mOoh);

    clearTimeout(mChangeTID);
    mChangeTID = setTimeout(changeComplete, 500);
}

function changeComplete() {
    mChangeStarted = false;

    if (radiiEqual(50) || radiiEqual(100)) {
        superHappy()
    }
    else {
        happy();
    }
}

function radiiEqual(v) {
    return  mSettings.borderTopLeftRadius === v && mSettings.borderTopRightRadius === v &&
        mSettings.borderBottomRightRadius === v && mSettings.borderBottomLeftRadius === v;
}

// other stuff

//mBlock.addEventListener('click', happy);
//happy();
